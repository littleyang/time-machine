package com.vanke.status.machine;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.mockito.InjectMocks;

import com.vanke.status.machine.service.StatusRouteService;
import com.vanke.test.base.BaseTest;


public class MockTest extends BaseTest{
	
	@InjectMocks
	private StatusRouteService routeService;
	
	@Test
	public void shouldReturnZeroWhenAllParamsPassed(){
		assertThat(routeService.getRouteNextStatusByTypeLastStatusEvent("heloo", 1000, "create"), is(0));
	}
	
	@Test
	public void shouldReturnOneNoTypeIfHasNotPassType(){
		assertThat(routeService.getRouteNextStatusByTypeLastStatusEvent("", 1000, "create"), is(1));
	}
	
	@Test
	public void shouldReturnTwoWhenNoLastStatus(){
		assertThat(routeService.getRouteNextStatusByTypeLastStatusEvent("hello", 0, "create"), is(2));
	}
	
	@Test
	public void shouldReturnThreeWhenNoEvent(){
		assertThat(routeService.getRouteNextStatusByTypeLastStatusEvent("hello", 1000, ""), is(3));
	}

}
