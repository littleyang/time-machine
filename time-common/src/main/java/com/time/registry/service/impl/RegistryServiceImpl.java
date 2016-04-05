package com.time.registry.service.impl;

import org.springframework.stereotype.Service;

import com.time.registry.service.RegistryDemoService;

@Service("registryDemoService")
public class RegistryServiceImpl implements RegistryDemoService {

	@Override
	public String sayHello(String name) {
		// TODO Auto-generated method stub
		return "hello, " + name;
	}

}
