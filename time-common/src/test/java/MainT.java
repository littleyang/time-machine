import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class MainT {
    public static void main(String args[]){
        AbstractApplicationContext context = new ClassPathXmlApplicationContext(
        		"application-contex-test.xml");
    }

}
