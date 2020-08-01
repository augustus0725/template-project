package com.sabo.flink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.state.ReducingState;
import org.apache.flink.api.common.state.ReducingStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.contrib.streaming.state.RocksDBStateBackend;
import org.apache.flink.shaded.guava18.com.google.common.base.Splitter;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.redis.RedisSink;
import org.apache.flink.streaming.connectors.redis.common.config.FlinkJedisPoolConfig;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisCommand;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisCommandDescription;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisMapper;
import org.apache.flink.util.Collector;
import org.apache.kafka.clients.consumer.ConsumerConfig;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class WordCount {

    private static FlinkKafkaConsumer<String> createFlinkKafkaConsumer() {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.0.76:9092");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "hongwang-001");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1024);
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return new FlinkKafkaConsumer<>("hongwang-trade", new SimpleStringSchema(), properties);
    }

    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.enableCheckpointing(30_000, CheckpointingMode.EXACTLY_ONCE); /* 30s一次快照，保证只处理一次 */
        env.getCheckpointConfig().setCheckpointTimeout(600_000); /* checkpoint 的超时时间 */
        env.getCheckpointConfig().setMinPauseBetweenCheckpoints(500); /* 两次checkpoint之间最短的间隔时间 */
        env.getCheckpointConfig().setMaxConcurrentCheckpoints(2); /* 设置checkpoint的并发数量 */

        // 配置状态的存储介质
        env.setStateBackend(new RocksDBStateBackend("file:///opt/rocksdb/data"));


        DataStreamSource<String> stream = env.addSource(createFlinkKafkaConsumer());
        stream.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
            @Override
            public void flatMap(String value, Collector<Tuple2<String, Integer>> out) throws Exception {
                for (String part : Splitter.on(',').split(value)) {
                    out.collect(new Tuple2<>(part, 1));
                }
            }
        })
                .keyBy(0)
                .timeWindow(Time.of(1, TimeUnit.SECONDS))
                .reduce((ReduceFunction<Tuple2<String, Integer>>) (value1, value2) -> new Tuple2<>(value1.f0, value1.f1 + value2.f1))
                // 上面的reduce能得到增量
                .keyBy(0)
                .flatMap(new RichFlatMapFunction<Tuple2<String, Integer>, Tuple2<String, Integer>>() {
                    private ReducingState<Tuple2<String, Integer>> state;

                    @Override
                    public void open(Configuration parameters) throws Exception {
                        super.open(parameters);

                        ReducingStateDescriptor<Tuple2<String, Integer>> descriptor = new ReducingStateDescriptor<Tuple2<String, Integer>>(
                                "count",
                                new ReduceFunction<Tuple2<String, Integer>>() {
                                    @Override
                                    public Tuple2<String, Integer> reduce(Tuple2<String, Integer> value1, Tuple2<String, Integer> value2) throws Exception {
                                        Tuple2<String, Integer> merge = new Tuple2<>(value1.f0, value1.f1 + value2.f1);
                                        return merge;
                                    }
                                },
                                TypeInformation.of(new TypeHint<Tuple2<String, Integer>>() {
                                })
                        );
                        state = getRuntimeContext().getReducingState(descriptor);
                    }

                    @Override
                    public void flatMap(Tuple2<String, Integer> value, Collector<Tuple2<String, Integer>> out) throws Exception {
                        state.add(value);
                        out.collect(state.get());
                    }
                }).addSink(
                new RedisSink<>(new FlinkJedisPoolConfig.Builder().setHost("192.168.0.76").build(),
                        new RedisMapper<Tuple2<String, Integer>>() {
                            @Override
                            public RedisCommandDescription getCommandDescription() {
                                return new RedisCommandDescription(RedisCommand.SET);
                            }

                            @Override
                            public String getKeyFromData(Tuple2<String, Integer> tuple2) {
                                return tuple2.f0;
                            }

                            @Override
                            public String getValueFromData(Tuple2<String, Integer> tuple2) {
                                return String.valueOf(tuple2.f1);
                            }
                        })

        );

        env.execute("word-count");
    }
}
