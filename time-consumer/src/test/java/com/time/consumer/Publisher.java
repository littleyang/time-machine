package com.time.consumer;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;

public class Publisher {



		public static void main(String[] args) throws JMSException {
			ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
			Connection connection = factory.createConnection();
			connection.start();
			
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Topic topic = session.createTopic("myTopic.messages");

			MessageProducer producer = session.createProducer(topic);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

			while(true) {
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

//			session.close();
//			connection.stop();
//			connection.close();
	
	}

}
