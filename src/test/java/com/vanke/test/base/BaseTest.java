package com.vanke.test.base;

import org.junit.Before;
import org.mockito.MockitoAnnotations;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:application-contex-test.xml" })
public class BaseTest {

	public BaseTest() {
		MockitoAnnotations.initMocks(this);
	}

//	@Before
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//    }
}
