package com.time.api;


import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.time.registry.service.RegistryDemoService;

public class Dubbo {
	
	public static void main(String[] args){
		
		String path = Dubbo.class.getClassLoader().getResource("").toString().replace("classes", "");
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "application-contex.xml" }); 
		
		context.start();
		
		RegistryDemoService demoService = (RegistryDemoService) context.getBean("registryDemoService");
		
		String hello = demoService.sayHello("zhangyun");
		
		System.out.println(hello);
	}

}
