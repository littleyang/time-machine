import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.time.common.kafka.consumer.TopicTestConsumer;


public class MainT {
    public static void main(String args[]){
        AbstractApplicationContext context = new ClassPathXmlApplicationContext(
        		"application-contex-test.xml");
        
        TopicTestConsumer c = (TopicTestConsumer) context.getBean("topicTestConsumer");
        c.listenner();
    }

}
