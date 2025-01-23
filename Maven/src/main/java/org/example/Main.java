package org.example;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("МАВИН ПРОЖЕКТ SAYS:");
        System.out.println("Hello world!");

        StringSerializer stringSerializer = new StringSerializer();
        StringDeserializer stringDeserializer = new StringDeserializer();

        String bootstrapServersConfig = ":9092,:9093,:9094";

        Properties producesProperties = new Properties();
//        producesProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9094,localhost:9096");
//        producesProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093,localhost:9094");
        producesProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServersConfig);
//        producesProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        producesProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, stringSerializer.getClass());
        producesProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, stringSerializer.getClass());

        Properties consumerProperties = new Properties();
//        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9094,localhost:9096");
//        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093,localhost:9094");
        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServersConfig);
//        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, stringDeserializer.getClass());
        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, stringDeserializer.getClass());
        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());

        KafkaProducer<String, String> producer = new KafkaProducer<>(producesProperties, stringSerializer, stringSerializer);
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProperties, stringDeserializer, stringDeserializer);



        System.out.println("Hello world!");

        String topic = "demka2";

        String messageValue = "bla bla " + Instant.now().atZone(ZoneId.systemDefault());
        Future<RecordMetadata> send = producer.send(new ProducerRecord<>(topic, messageValue));
        RecordMetadata sendGet = send.get();

        System.out.println(send);
        System.out.println(sendGet);

//        consumer.subscribe(List.of(topic));
        List<TopicPartition> assignings = consumer.partitionsFor(topic).stream()
                .map(partitionInfo -> new TopicPartition(partitionInfo.topic(), partitionInfo.partition()))
                .toList();
        consumer.assign(assignings);
        consumer.seekToBeginning(Collections.emptyList());
        ConsumerRecords<String, String> poll = consumer.poll(Duration.of(5, ChronoUnit.SECONDS));
        consumer.seekToBeginning(Collections.emptyList());
//        consumer.beginningOffsets()
//        poll = consumer.poll(Duration.of(5, ChronoUnit.SECONDS));
//        ConsumerRecords<String, String> poll = consumer.poll(Duration.of(5, ChronoUnit.SECONDS));

        List<ConsumerRecord<String, String>> pollList = new ArrayList<>();
        poll.iterator().forEachRemaining(pollList::add);

        System.out.println(poll);
        System.out.println(pollList);


        boolean isCorrect = pollList.stream().map(ConsumerRecord::value).toList().contains(messageValue);

        if (isCorrect) {
            System.out.println("ВСЁ ВОРКАЕТ НОРМ!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        } else {
            System.out.println("не работает..");
        }

        System.out.println("Hello world!");
    }
}