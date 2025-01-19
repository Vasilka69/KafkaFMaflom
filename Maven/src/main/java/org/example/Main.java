package org.example;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        System.out.println("МАВИН ПРОЖЕКТ SAYS:");
        System.out.println("Hello world!");

        StringSerializer stringSerializer = new StringSerializer();
        StringDeserializer stringDeserializer = new StringDeserializer();

        Properties producesProperties = new Properties();
        producesProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "broker:9092,localhost:9093,localhost:9094");
        producesProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, stringSerializer.getClass());
        producesProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, stringSerializer.getClass());

        Properties consumerProperties = new Properties();
        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093,localhost:9094");
        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, stringDeserializer.getClass());
        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, stringDeserializer.getClass());
//        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());

        KafkaProducer<String, String> producer = new KafkaProducer<>(producesProperties, stringSerializer, stringSerializer);
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProperties, stringDeserializer, stringDeserializer);



        System.out.println("Hello world!");


        producer.send(new ProducerRecord<>("demo", "bla bla"));


        System.out.println("Hello world!");
    }
}