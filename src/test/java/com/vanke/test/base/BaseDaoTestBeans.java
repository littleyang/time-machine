package com.vanke.test.base;

import org.junit.After;
import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.unitils.UnitilsJUnit4;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringApplicationContext;

/**
 * base DAO test class,all subclass extends this
 * @author yangyang
 *
 */

@DataSet({"/data/task-events-test-data.xml",
	"/data/task-status-test-data.xml"})
public class BaseDaoTestBeans extends UnitilsJUnit4 {
	
	@SpringApplicationContext({ "classpath:application-contex-test.xml"})  
	protected ApplicationContext applicationContext;  
	
	
	@Before
	public void beforeSetUp(){
		System.out.println("\n========before test settings=========\n");
	}
	
	@After
	public void afterSetUp(){
		System.out.println("\n========after test settings===========\n");
	}

}