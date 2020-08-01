package com.sabo.cdh.kerberos.kafka;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * @author canbin.zhang
 * date: 2019/11/29
 */
public class KafkaConsumerTemplate {
    private Consumer<String, String> createConsumer() {
        System.setProperty("java.security.auth.login.config", "kafka-conf/kafka-jaas.conf");
        System.setProperty("java.security.krb5.conf", "kafka-conf/krb5.conf");

        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "cdh01.hw.com:9092,cdh03.hw.com:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "hongwang-001");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1024);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put("security.protocol", "SASL_PLAINTEXT");

        Consumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList("hongwang-trade"));
        return consumer;
    }

    private void runConsumer() {
        Consumer<String, String> consumer = createConsumer();
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
            System.out.println("===================: " + records.count());

            for (ConsumerRecord record : records) {
                System.out.println(record.value());
            }
        }
    }

    public static void main(String[] args) {
        new KafkaConsumerTemplate().runConsumer();
    }
}
