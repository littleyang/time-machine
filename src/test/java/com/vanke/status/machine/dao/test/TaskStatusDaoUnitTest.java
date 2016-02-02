package com.vanke.status.machine.dao.test;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.jdbc.core.JdbcTemplate;

import com.vanke.common.constant.CommonCodeConst;
import com.vanke.common.exceptions.BaseDaoException;
import com.vanke.status.machine.dao.TaskStatusDao;
import com.vanke.status.machine.dao.crud.TaskStatusCrudDao;
import com.vanke.status.machine.model.TaskStatus;
import com.vanke.test.base.BaseTestUnit;

public class TaskStatusDaoUnitTest extends BaseTestUnit {
	
	@Mock
	private JdbcTemplate jdbcTemplate;
	
	@Mock
	private TaskStatusCrudDao taskStatusCrudDao;
	
	@InjectMocks
	private TaskStatusDao taskStatusDao;
	
	@Test
	public void testGetAllStatusCount(){
		assertThat(taskStatusDao.getAllTaskStatusCountByJdbc(), is(0));
	}
	
	@Test
	public void testCreateTaskStatus(){
		TaskStatus status = new TaskStatus();
		status.setId(10000);
		status.setName("test-status");
		status.setOuterStatus(10000);
		status.setOuterName("test-out-name");
		status.setType(0);
		status.setCreated(new Date());
		status.setUpdated(new Date());
		
		TaskStatus created = taskStatusDao.createTaskStatus(status);
		assertThat("should be null",created, is(nullValue()));
		
	}
	
	@Test
	public void testGetTaskStatusByIdByJdbc() throws BaseDaoException{
		int statusId = 1000;
		TaskStatus one = taskStatusDao.getTaskStatusByIdByJdbc(statusId);
		assertThat("should be null",one, is(nullValue()));
	}
	
	
	@Test
	public void testGetTaskStatusByStatusByJdbc(){
		int status = 0;
		TaskStatus one;
		try {
			one = taskStatusDao.getTaskStatusByStatusByJdbc(status);
			assertThat("should be null",one, is(nullValue()));
		} catch (BaseDaoException e) {
			// TODO Auto-generated catch block
			assertThat("should be null",e.getCode(), is(CommonCodeConst.QUERY_PARAMS_ERROR_CODE));
		}
	}
	
	
	@Test(expected=BaseDaoException.class)
	public void testByAnotherWayGetTaskStatusByStatusByJdbc() throws BaseDaoException{
		int status = 0;
		TaskStatus one;
		one = taskStatusDao.getTaskStatusByStatusByJdbc(status);
		assertThat("should be null",one, is(nullValue()));
	}
	
}




