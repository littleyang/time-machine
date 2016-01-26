package com.vanke.status.machine.dao.test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.jdbc.core.JdbcTemplate;

import com.vanke.status.machine.dao.TaskRoutesDao;
import com.vanke.status.machine.dao.crud.TaskRoutesCrudDao;
import com.vanke.status.machine.model.TaskRoutes;
import com.vanke.test.base.BaseTestUnit;

public class TaskRoutesDaoUnitTest extends BaseTestUnit {
	
	@Mock
	private JdbcTemplate jdbcTemplate;
	
	@Mock
	private TaskRoutesCrudDao taskRoutesCrudDao;
	
	@InjectMocks
	private TaskRoutesDao taskRoutesDao;
	
	TaskRoutes route = new TaskRoutes();
	
	@Before
	public void setUp(){
		System.out.println("======set up all test data ======");
		route.setId(10000);
		route.setBussinessCode("test-business-code");
		route.setCurrentEvent("current-test-event");
		route.setCurrentStatus(10001);
		route.setNextEvent("next-test-event");
		route.setNextStatus(10002);
	}
	
	@After
	public void cleanAll(){
		System.out.println("======clean all test data ======");
		//taskRoutesDao.deleteAllTaskRoutes();
	}
	
	
	@Test
	public void testGetAllStatusCount(){
		assertThat(taskRoutesDao.getAllRoutesCountByJdbc(), is(0));
	}
	
	
	@Test
	public void testCreateTaskStatus(){
		TaskRoutes created = taskRoutesDao.create(route);
		assertThat("should be null", route, is(notNullValue()));
		assertThat("should be null",created, is(nullValue()));
		
	}
	
//	@Test
//	public void testGetTaskStatusByIdByJdbc() throws BaseDaoException{
//		int statusId = 1000;
//		TaskStatus one = taskStatusDao.getTaskStatusByIdByJdbc(statusId);
//		assertThat("should be null",one, is(nullValue()));
//	}
//	
//	
//	@Test
//	public void testGetTaskStatusByStatusByJdbc(){
//		int status = 0;
//		TaskStatus one;
//		try {
//			one = taskStatusDao.getTaskStatusByStatusByJdbc(status);
//			assertThat("should be null",one, is(nullValue()));
//		} catch (BaseDaoException e) {
//			// TODO Auto-generated catch block
//			assertThat("should be null",e.getCode(), is(ResponesCodeConst.QUERY_PARAMS_ERROR_CODE));
//		}
//	}
//	
//	
//	@Test(expected=BaseDaoException.class)
//	public void testByAnotherWayGetTaskStatusByStatusByJdbc() throws BaseDaoException{
//		int status = 0;
//		TaskStatus one;
//		one = taskStatusDao.getTaskStatusByStatusByJdbc(status);
//		assertThat("should be null",one, is(nullValue()));
//	}
	
}




