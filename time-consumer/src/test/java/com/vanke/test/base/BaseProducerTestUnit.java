package com.vanke.test.base;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.unitils.UnitilsJUnit4;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-contex-producer-test.xml" })
public class BaseProducerTestUnit extends UnitilsJUnit4{
	
	public BaseProducerTestUnit() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testOk(){
		Assert.assertTrue(true);
	}
}
