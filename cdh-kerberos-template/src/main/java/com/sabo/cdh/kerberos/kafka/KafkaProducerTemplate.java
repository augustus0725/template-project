package com.sabo.cdh.kerberos.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * @author canbin.zhang
 * date: 2019/11/29
 */
public class KafkaProducerTemplate {
    private Producer<Long, String> initKafkaProducer(String brokerList) {
        System.setProperty("java.security.auth.login.config", "kafka-conf/kafka-jaas.conf");
        System.setProperty("java.security.krb5.conf", "kafka-conf/krb5.conf");

        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 1024);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 10);
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", StringSerializer.class.getName());
        props.put("security.protocol", "SASL_PLAINTEXT");
        KafkaProducer<Long, String> producer = new KafkaProducer<>(props);
        // 会话期间, 开启事务
        producer.initTransactions();
        return producer;
    }

    private void runProducer() {
        Producer<Long, String> producer = initKafkaProducer("cdh01.hw.com:9092,cdh03.hw.com:9092");
        try {
            // 开始事务
            producer.beginTransaction();
            for (int i = 0; i < 100000; i++) {
                producer.send(new ProducerRecord<>("hongwang-trade", "{\n" +
                        "\t\"name\": \"sabo" + i + "\",\n" +
                        "\t\"age\": 100,\n" +
                        "\t\"price\": 10.3\n" +
                        "}"), (metadata, exception) -> {
                    System.out.println("hello");
                });
            }
            // 完成事务
            producer.commitTransaction();
        } catch (Exception e) {
            // 终止事务
            producer.abortTransaction();
        } finally {
            producer.close();
        }
    }

    public static void main(String[] args) {
        new KafkaProducerTemplate().runProducer();
    }

}
