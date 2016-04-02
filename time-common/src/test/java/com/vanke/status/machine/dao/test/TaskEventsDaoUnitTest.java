package com.vanke.status.machine.dao.test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.jdbc.core.JdbcTemplate;

import com.vanke.status.machine.dao.TaskEventsDao;
import com.vanke.test.base.BaseTestUnit;

public class TaskEventsDaoUnitTest extends BaseTestUnit{
	
	@Mock
	private JdbcTemplate jdbcTemplate;
	
	@InjectMocks
	private TaskEventsDao eventsDao;
	
	@Test
	public void testGetAllStatusEventsShouldBeZero(){
		assertThat(eventsDao.getAllEventsCountByJdbc(), is(0));
	}


}
