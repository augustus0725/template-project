package com.sabo.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class KafkaProducerSample {
    public static final String SABO = "sabo-2";
    private static final String BOOTSTRAP_SERVERS = "192.168.1.120:9092";

    private static Logger logger = LoggerFactory
            .getLogger(KafkaProducerSample.class);

    void run() {
        Producer<Long, String> producer = initKafkaProducer(BOOTSTRAP_SERVERS);
        try {
            for (int i = 0; i < 10000; i++) {
                producer.send(new ProducerRecord<>(SABO, "Nimbus"), (metadata, exception) -> {
                    System.out.println("Nimbus");
                });
                producer.send(new ProducerRecord<>(SABO, "World"), (metadata, exception) -> {
                    System.out.println("World");
                });
            }
        } finally {
            producer.close();
        }
    }

    public Producer<Long, String> initKafkaProducer(String brokerList){
        Properties props = new Properties();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);//格式：host1:port1,host2:port2,....
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 1024);//a batch size of zero will disable batching entirely
        props.put(ProducerConfig.LINGER_MS_CONFIG, 10);//send message without delay
        props.put(ProducerConfig.ACKS_CONFIG, "all");//对应partition的leader写到本地后即返回成功。极端情况下，可能导致失败
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", StringSerializer.class.getName());
        return new org.apache.kafka.clients.producer.KafkaProducer<>(props);
    }

    public static void main(String[] args) {
        try {
            new KafkaProducerSample().run();
        } catch (Exception ignore) {}
    }
}
