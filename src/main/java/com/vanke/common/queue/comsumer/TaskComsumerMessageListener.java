package com.vanke.common.queue.comsumer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.vanke.common.model.base.BaseObject;

@Component("taskComsumerMessageListener")
public class TaskComsumerMessageListener implements MessageListener{

	protected Log logger = LogFactory.getLog(getClass());
	
	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		 if (message instanceof ObjectMessage){
			 try {
				BaseObject object = ((BaseObject) (((ObjectMessage) message).getObject()));
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				logger.info("＝＝＝＝＝＝＝处理任务消息错误！＝＝＝＝＝＝＝＝" + e.getMessage());
			} 
		 }
		
	}

}
