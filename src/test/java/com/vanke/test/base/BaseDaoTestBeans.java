package com.vanke.test.base;

import org.springframework.context.ApplicationContext;
import org.unitils.UnitilsJUnit4;
import org.unitils.spring.annotation.SpringApplicationContext;

public class BaseDaoTestBeans extends UnitilsJUnit4 {
	
	@SpringApplicationContext({ "classpath:application-contex-test.xml"})  
	protected ApplicationContext applicationContext;  

}
