package com.vanke.test.base;

import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-contex-test.xml" })
public class BaseTest {

	public BaseTest() {
		MockitoAnnotations.initMocks(this);
	}
	
}
