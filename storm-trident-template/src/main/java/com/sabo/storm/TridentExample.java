package com.sabo.storm;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.kafka.spout.Func;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
import org.apache.storm.kafka.spout.KafkaSpoutRetryExponentialBackoff;
import org.apache.storm.kafka.spout.KafkaSpoutRetryService;
import org.apache.storm.kafka.spout.trident.KafkaTridentSpoutOpaque;
import org.apache.storm.redis.common.config.JedisClusterConfig;
import org.apache.storm.redis.trident.state.RedisClusterMapState;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.operation.BaseFunction;
import org.apache.storm.trident.operation.CombinerAggregator;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.state.StateFactory;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.apache.storm.kafka.spout.KafkaSpoutConfig.FirstPollOffsetStrategy.EARLIEST;


public class TridentExample {
    private static final String KAFKA_LOCAL_BROKER = "192.168.1.170:9092";
    public static final String TOPIC = "sabo-2";

    public static class Split extends BaseFunction {
        @Override
        public void execute(TridentTuple tuple, TridentCollector collector) {
            String sentence = tuple.getString(0);
            for (String word : sentence.split(" ")) {
                collector.emit(new Values(word));
            }
        }
    }

    private static KafkaTridentSpoutOpaque<String, String> newKafkaTridentSpoutOpaque() {
        return new KafkaTridentSpoutOpaque<>(newKafkaSpoutConfig());
    }

    protected static KafkaSpoutConfig<String, String> newKafkaSpoutConfig() {
        return KafkaSpoutConfig.builder(KAFKA_LOCAL_BROKER, TOPIC)
                .setProp(ConsumerConfig.GROUP_ID_CONFIG, "kafkaSpoutTestGroup_" + System.nanoTime())
                .setProp(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, 200)
                .setRecordTranslator(JUST_VALUE_FUNC, new Fields("sentence"))
                .setRetry(newRetryService())
                .setOffsetCommitPeriodMs(10_000)
                .setFirstPollOffsetStrategy(EARLIEST)
                .setMaxUncommittedOffsets(250)
                .build();
    }

    private static Func<ConsumerRecord<String, String>, List<Object>> JUST_VALUE_FUNC = new JustValueFunc();

    private static class JustValueFunc implements Func<ConsumerRecord<String, String>, List<Object>>, Serializable {
        @Override
        public List<Object> apply(ConsumerRecord<String, String> record) {
            return new Values(record.value());
        }
    }

    protected static KafkaSpoutRetryService newRetryService() {
        return new KafkaSpoutRetryExponentialBackoff(new KafkaSpoutRetryExponentialBackoff.TimeInterval(500L, TimeUnit.MICROSECONDS),
                KafkaSpoutRetryExponentialBackoff.TimeInterval.milliSeconds(2), Integer.MAX_VALUE, KafkaSpoutRetryExponentialBackoff.TimeInterval.seconds(10));
    }

    private static StateFactory createRedisStateFactory() {
        JedisClusterConfig.Builder builder = new JedisClusterConfig.Builder().setNodes(new HashSet<>(Arrays.asList(
                new InetSocketAddress("192.168.1.170", 6379),
                new InetSocketAddress("192.168.1.170", 7379),
                new InetSocketAddress("192.168.1.170", 8379)
        )));
        return RedisClusterMapState.opaque(builder.build());
    }

    public static StormTopology buildTopology() {
        TridentTopology topology = new TridentTopology();
        topology.newStream("spout1", newKafkaTridentSpoutOpaque())
                .parallelismHint(2)
                .each(new Fields("sentence"),
                        new Split(),
                        new Fields("word"))
                .groupBy(new Fields("word"))
                .persistentAggregate(createRedisStateFactory(),
                        new Fields("word"),
                        new CombinerAggregator<Long>() {
                            @Override
                            public Long init(TridentTuple tuple) {
                                return 1L;
                            }

                            @Override
                            public Long combine(Long val1, Long val2) {
                                System.out.printf("val1: %d, val2: %d, total = %d%n", val1, val2, val1 + val2);
                                return val1 + val2;
                            }

                            @Override
                            public Long zero() {
                                return 0L;
                            }
                        },
                        new Fields("count")
                )
                .parallelismHint(1);
        return topology.build();
    }

    public static void main(String[] args) throws Exception {

        Config conf = new Config();
        conf.setMaxSpoutPending(20);
        if (args.length == 0) {
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("wordCounter", conf, buildTopology());

            Utils.sleep(600_000);
        } else {
            conf.setNumWorkers(2);
            StormSubmitter.submitTopologyWithProgressBar(args[0], conf, buildTopology());
        }
    }
}

