package com.time.common.kafka.consumer;

import java.util.Arrays;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.stereotype.Component;

//@Component("topicTestConsumer")
public class TopicTestConsumer {

	@SuppressWarnings("rawtypes")
	@Autowired
	@Qualifier("kafkaConsumerFactory")
	private DefaultKafkaConsumerFactory kafkaConsumerFactory;

	@SuppressWarnings("unchecked")
	public void listenner() {
		KafkaConsumer<String, String> consumer = (KafkaConsumer<String, String>) kafkaConsumerFactory.createConsumer();
		consumer.subscribe(Arrays.asList("nginx"));
		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(100);
			for (ConsumerRecord<String, String> record : records)
				// System.out.printf("offset = %d, key = %s, value = %s \n",
				// record.offset(), record.key(), record.value());
				if (record.value().contains("/api/lebang/staffs/me/work")) {
					System.out.println("员工开始签到了 ………………………… " + record.value());
				}
		}
	}

}
