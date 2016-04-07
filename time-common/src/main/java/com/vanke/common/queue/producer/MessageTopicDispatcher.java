package com.vanke.common.queue.producer;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import com.vanke.common.model.base.BaseObject;


@Component("messageTopicDispatcher")
public class MessageTopicDispatcher {
	
	/**
	 * Jms消息模版
	 */
	@Autowired
	@Qualifier("jmsTopicTemplate")
	private JmsTemplate jmsTopicTemplate;
	
	/**
	 * 任务队列
	 */
	@Autowired
	@Qualifier("taskTopicDestination")
	private Destination taskTopicDestination;
	
	
	
	/**
	 * 将消息分发到任务队列
	 * @param object
	 */
	public void publishToTaskTopicDestination(final BaseObject object){
		
		MessageCreator messageCreator = new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				ObjectMessage message = session.createObjectMessage();
				message.setObject(object);
				return message;
			}
		};

		jmsTopicTemplate.send(this.taskTopicDestination, messageCreator);
	}

}
