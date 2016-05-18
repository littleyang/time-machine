package com.time.common.kafka.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

@Component("testTopicListener")
public class TestTopicListener implements MessageListener<String,String>{
	
	@SuppressWarnings("rawtypes")
	@Autowired
	@Qualifier("kafkaConsumerFactory")
	private DefaultKafkaConsumerFactory kafkaConsumerFactory;

	@Override
	public void onMessage(ConsumerRecord<String,String> record) {
		// TODO Auto-generated method stub
		if (record.value().contains("/api/lebang/staffs/me/work")) {
			System.out.println("员工开始签到了 ………………………… " + record.value());
		}
	}

}
