package com.time.consumer;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class KafkaProducerA {

	public static String topicName = "test";

	public static void main(String[] args) throws InterruptedException {

		Properties props = new Properties();
		//props.put("bootstrap.servers", "localhost:9092");
		//props.put("bootstrap.servers", "127.0.0.1:9092");
		props.put("bootstrap.servers", "10.0.72.51:9092");
		props.put("acks", "all");
		props.put("retries", 0);
		props.put("batch.size", 16384);
		props.put("linger.ms", 1);
		props.put("buffer.memory", 33554432);
		props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");

		Producer<String, String> producer = new KafkaProducer(props);
		for (int i = 0; i < 100; i++) {
			ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(
					topicName, Integer.toString(i), Integer.toString(i));
			System.out.println(producerRecord);
			producer.send(producerRecord);
		}
		producer.close();
	}
}
