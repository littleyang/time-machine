package com.time.consumer;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainK {
	
	 public static void main(String args[]){
	        AbstractApplicationContext context = new ClassPathXmlApplicationContext(
	        		"application-consumer-contex-test.xml");
	    }

}
