package com.time.consumer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.vanke.common.model.base.BaseObject;

@Component("taskMessageSubscriberListener")
public class TaskMessageSubscriberListener implements MessageListener{

	protected Log logger = LogFactory.getLog(getClass());
	
	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		 if (message instanceof ObjectMessage){
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
				logger.info("＝＝＝＝＝＝＝处理任务消息错误！＝＝＝＝＝＝＝＝" + e.getMessage());
			} 
		 }
		
	}

	private void createTask() {
		// TODO Auto-generated method stub
		logger.info("===========this is create task !!!=============");
	}

}
