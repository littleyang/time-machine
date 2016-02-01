package com.vanke.common.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.vanke.common.exceptions.BaseDaoException;
import com.vanke.common.model.task.Task;
import com.vanke.common.task.dao.TaskDao;
import com.vanke.status.machine.dao.TaskEventsDao;
import com.vanke.status.machine.dao.TaskRoutesDao;
import com.vanke.status.machine.dao.TaskStatusDao;
import com.vanke.status.machine.service.TaskStatusMachineService;

@Service("taskService")
public class TaskService {
	
	@Autowired
	@Qualifier("taskDao")
	private TaskDao taskDao;
	
	@Autowired
	@Qualifier("taskRoutesDao")
	private TaskRoutesDao taskRoutesDao;
	
	@Autowired
	@Qualifier("taskEventsDao")
	private TaskEventsDao taskEventsDao;
	
	@Autowired
	@Qualifier("taskStatusDao")
	private TaskStatusDao taskStatusDao;
	
	@Autowired
	@Qualifier("taskStatusMachineService")
	private TaskStatusMachineService taskStatusMachineService;
	
	/**
	 * 
	 * @return
	 */
	public String createTaskNo(){
		return "T"+Long.toString(System.currentTimeMillis());
	}
	
	/**
	 * 
	 * @param task
	 * @return
	 * @throws BaseDaoException
	 */
	public Task createTask(Task task) throws BaseDaoException{
		return taskDao.createTask(task);
	}
	
	
	public void deleteAllTask(){
		taskDao.deleteAllTask();
	}
	
	
}
