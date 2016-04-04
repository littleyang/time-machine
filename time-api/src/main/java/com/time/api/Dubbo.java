package com.time.api;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;

import com.time.registry.service.RegistryService;

public class Dubbo {
	
	public static void main(String[] args){
		String path = Dubbo.class.getClassLoader().getResource("").toString()  
                .replace("classes", "");
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(  
                new String[] { "application-contex.xml" }); 
		context.start();
		RegistryService demoService = (RegistryService) context.getBean("registryServiceDemo");
		
		String hello = demoService.sayHello("xupei");
		
		System.out.println(hello);
	}

}
