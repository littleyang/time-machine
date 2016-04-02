package com.vanke.status.machine.dao.test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.unitils.spring.annotation.SpringBeanByType;

import com.vanke.common.exceptions.BaseDaoException;
import com.vanke.common.model.task.Task;
import com.vanke.common.task.dao.TaskDao;
import com.vanke.test.base.BaseDaoTestBeans;

public class TaskDaoDataTest extends BaseDaoTestBeans{
	
	@SpringBeanByType 
	private TaskDao taskDao;
	
	Task task = new Task();
	
	@Before
	public void setUp(){
		System.out.println("======set up all test data ======");
		task.setTaskNo("1911101001");
		task.setBusinessType("BUCR020103");
		task.setStatus(1000);
	}
	
	@After
	public void clean(){
		System.out.println("======clean all test data ======");
		taskDao.deleteAllTask();;
	}
	
	@Test
	public void testGetAllTaskCount(){
		assertThat("all task events count should be 2",taskDao.getAllTaskCountByJdbc(), is(2));
	}
	
	@Test
	public void testGetTaskByIdByCrudDao() throws BaseDaoException{
		Task taskTemp = taskDao.findById(1);
		assertThat("task task id should be equal",taskTemp.getId(), is(1));
		assertThat("task task no should be equal ",taskTemp.getTaskNo(), is("201601290001"));
	}
	
	@Test
	public void testGetTaskByTaskNoByCrudDao() throws BaseDaoException{
		String taskNo = "201601290001";
		Task taskTemp = taskDao.findByTaskNo(taskNo);
		assertThat("task id should be equal",taskTemp.getId(), is(1));
		assertThat("task no should be equal init ",taskTemp.getTaskNo(), is(taskNo));
	}
	
	@Test
	public void testCreateTaskEventByCrudDao() throws BaseDaoException{
		Task taskTemp = taskDao.createTask(task);
		assertThat("task should be not null ",taskTemp, is(notNullValue()));
		assertThat("task should be equal ",task.getTaskNo(), is(taskTemp.getTaskNo()));
	}
	
	@Test
	public void testDeleteTaskEventByCrudDao() throws BaseDaoException{
		Task taskTemp = taskDao.createTask(task);
		taskDao.deleteTask(taskTemp);
		Task deletedTask = taskDao.findByTaskNo(task.getTaskNo());
		assertNull(deletedTask);
		assertThat("task should be equal ",deletedTask, is(nullValue()));
	}
	
	@Test
	public void testGetTaskByIdByJdbc() throws BaseDaoException{
		Task create = taskDao.createTask(task);
		Task findone = taskDao.getTaskByIdByJdbc(create.getId());
		assertNotNull(findone);
		assertThat("task should be equal ",create.getTaskNo(), is(findone.getTaskNo()));
	}
	
	
	@Test
	public void testGetTaskByTaskNoByJdbc() throws BaseDaoException{
		Task create = taskDao.createTask(task);
		Task findone = taskDao.getTaskByTaskNoByJdbc(create.getTaskNo());
		assertNotNull(findone);
		assertThat("task should be equal ",create.getId(), is(findone.getId()));
		assertThat("task should be equal ",create.getTaskNo(), is(findone.getTaskNo()));
	}
	

}
