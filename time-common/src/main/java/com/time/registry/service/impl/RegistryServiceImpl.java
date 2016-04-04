package com.time.registry.service.impl;

import org.springframework.stereotype.Service;

import com.time.registry.service.RegistryService;

@Service("registryService")
public class RegistryServiceImpl implements RegistryService {

	@Override
	public String sayHello(String name) {
		// TODO Auto-generated method stub
		return "hello, " + name;
	}

}
