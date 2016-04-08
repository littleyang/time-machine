package com.time.consumer;
import org.apache.activemq.ActiveMQConnectionFactory;  

import com.vanke.common.model.base.BaseObject;

import javax.jms.*;


public class Subscriber {
	public static void main(String[] args) throws JMSException {
		
		//System.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES","*");
		
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		factory.setTrustAllPackages(true);
		Connection connection = factory.createConnection();
		
		connection.start();

		Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("taskTopic");

		MessageConsumer consumer = session.createConsumer(topic);
		
		consumer.setMessageListener(new MessageListener() {
			public void onMessage(Message message) {
				
				if(message instanceof ObjectMessage){
					try {
						BaseObject object = ((BaseObject) (((ObjectMessage) message).getObject()));
						switch (object.getBaseType()){
						case "CreateTask":
							createTask();
						default:
							break;
						}
					} catch (JMSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
				if (message instanceof TextMessage){
					try {
						System.out.println("处理任务消息=======接收到消息:"+ ((TextMessage) message).getText());
					} catch (JMSException e) {
						// TODO Auto-generated catch block
						System.out.println("＝＝＝＝＝＝＝处理任务消息错误！＝＝＝＝＝＝＝＝" + e.getMessage());
					}
				}
			}

			private void createTask() {
				// TODO Auto-generated method stub
				System.out.println("===========this is create task !!!=============");
			}
		});
		
		
		 session.close();
		 connection.stop();
		 connection.close();
	}
}
