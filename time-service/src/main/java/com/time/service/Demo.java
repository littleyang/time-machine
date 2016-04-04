package com.time.service;

import org.springframework.context.support.ClassPathXmlApplicationContext;


import com.time.registry.service.RegistryDemoService;

public class Demo {

public static void main(String[] args){
		
	
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "application-contex.xml" }); 
		
		context.start();
		
		RegistryDemoService demoService = (RegistryDemoService) context.getBean("registryDemoService");
		
		String hello = demoService.sayHello("zhou yang");
		
		System.out.println(hello);
	}
	
}
