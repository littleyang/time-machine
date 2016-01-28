package com.vanke.status.machine.dao.test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.jdbc.core.JdbcTemplate;

import com.vanke.common.exceptions.BaseDaoException;
import com.vanke.common.task.dao.TaskCrudDao;
import com.vanke.common.task.dao.TaskDao;
import com.vanke.test.base.BaseTestUnit;

public class TaskDaoUnitTest extends BaseTestUnit{
	
	@Mock
	private JdbcTemplate jdbcTemplate;
	
	@Mock
	private TaskCrudDao taskCrudDao;
	
	@InjectMocks
	private TaskDao taskDao;
	
	@Test
	public void testGetAllStatusEventsShouldBeZero(){
		assertThat(taskDao.getAllTaskCountByJdbc(), is(0));
	}
	
	@Test(expected=BaseDaoException.class)
	public void testCreateTaskIfTaskNullShouldException() throws BaseDaoException{
		taskDao.createTask(null);
	}
	
	@Test(expected=BaseDaoException.class)
	public void testFindOneByIdShouldBeExceptionIfIdIsZero() throws BaseDaoException{
		taskDao.findById(0);
	}
	
	@Test(expected=BaseDaoException.class)
	public void findOneTaskByTaskNoShouldBeExceptionIfTaskNoIsNull() throws BaseDaoException{
		taskDao.findByTaskNo(null);
	}
	
	@Test(expected=BaseDaoException.class)
	public void getTaskByIdByJdbcShouldBeExceptionIfTaskIdIsZero() throws BaseDaoException{
		taskDao.getTaskByIdByJdbc(0);
	}
	
	@Test(expected=BaseDaoException.class)
	public void testGetTaskByJdbcByTaskNoShouldBeExceptionIfTaskNoIsNull() throws BaseDaoException{
		taskDao.getTaskByTaskNoByJdbc(null);
	}

}
