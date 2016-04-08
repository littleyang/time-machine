package com.time.consumer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.vanke.common.model.base.BaseObject;

@Component("taskTextMessageSubscriberListener")
public class TaskTextMessageSubscriberListener implements MessageListener {

	protected Log logger = LogFactory.getLog(getClass());

	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		if (message instanceof ObjectMessage) {
			try {
				BaseObject object = ((BaseObject) (((ObjectMessage) message).getObject()));
				switch (object.getBaseType()) {
				case "CreateTask":
					createTask();
				default:
					break;
				}
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				logger.info("＝＝＝＝＝＝＝处理任务消息错误！＝＝＝＝＝＝＝＝" + e.getMessage());
			}
		}

		if (message instanceof TextMessage) {
			try {
				System.out.println("处理任务消息=======接收到消息:" + ((TextMessage) message).getText());
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				logger.info("＝＝＝＝＝＝＝处理任务消息错误！＝＝＝＝＝＝＝＝" + e.getMessage());
			}
		}

	}

	private void createTask() {
		// TODO Auto-generated method stub
		System.out.println("===========this is create task !!!=============");
		logger.info("===========this is create task !!!=============");
	}

}
