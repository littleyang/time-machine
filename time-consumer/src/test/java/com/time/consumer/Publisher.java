package com.time.consumer;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.vanke.common.model.base.BaseObject;

import javax.jms.*;

public class Publisher {

		public static void main(String[] args) throws JMSException {
			ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
			factory.setTrustAllPackages(true);
			Connection connection = factory.createConnection();
			connection.start();
			
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Topic topic = session.createTopic("taskTopic");

			MessageProducer producer = session.createProducer(topic);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

			for(int i=0;i<20;i++){
				TextMessage message = session.createTextMessage();
				message.setText("message_" + System.currentTimeMillis());
				producer.send(message);
				System.out.println("Sent message: " + message.getText());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			for(int i=0;i<20;i++){
				ObjectMessage message = session.createObjectMessage();
				BaseObject testObject = new BaseObject();
				testObject.setBaseType("CreateTask");
				message.setObject(testObject);
				producer.send(message);
				System.out.println("Sent CreateTask : CreateTask " + i);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			session.close();
			connection.stop();
			connection.close();
	
	}

}
