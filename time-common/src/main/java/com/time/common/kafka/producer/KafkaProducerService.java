package com.time.common.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service("kafkaProducerService")
public class KafkaProducerService {
	
	@Autowired
	@Qualifier("kafkaProducerTemplate")
	private KafkaTemplate<Object, Object> kafkaTemplate;
	
	
	/**
	 * send data to topic
	 */
	public void sendMessageToTopic(String topic, Object message){
		kafkaTemplate.send(topic,message);
	}

}
