package com.vanke.common.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.vanke.common.constant.CommonCodeConst;
import com.vanke.common.data.vo.TaskSnapshot;
import com.vanke.common.exceptions.BaseDaoException;
import com.vanke.common.exceptions.BaseServiceException;
import com.vanke.common.model.task.Task;
import com.vanke.common.task.dao.TaskDao;
import com.vanke.status.machine.dao.TaskEventsDao;
import com.vanke.status.machine.dao.TaskRoutesDao;
import com.vanke.status.machine.dao.TaskStatusDao;
import com.vanke.status.machine.model.TaskEvents;
import com.vanke.status.machine.model.TaskRoutes;
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
	
	/**
	 * 
	 * @param taskNo
	 * @param operationType
	 * @return
	 * @throws BaseDaoException
	 * @throws BaseServiceException
	 */
	public TaskSnapshot getTaskDetail(String taskNo, int operationType) throws BaseDaoException, BaseServiceException{
		// 检查传递的参数是否有效
		if(null==taskNo||taskNo.equals("")){
			throw new BaseServiceException(CommonCodeConst.PROCESS_ERROR,"任务操作参数有错误");
		}
		
		Task task = taskDao.getTaskByTaskNoByJdbc(taskNo);
		
		if(null==task){
			throw new BaseServiceException(CommonCodeConst.PROCESS_ERROR,"任务操作参数有错误");
		}
		
		List<TaskRoutes> taskNextEventRoutes = taskRoutesDao.findNextTaskRouteEvents(task.getBusinessType(), task.getStatus(), operationType);
		
		// 查找下一个操作事件
		List<TaskEvents> nextEvents = taskEventsDao.findNextEventsByRoutes(taskNextEventRoutes);
		
		TaskSnapshot result = new TaskSnapshot();
		
		result.setTask(task);
		
		result.setOperations(nextEvents);
		
		return result;
	}
	
	/**
	 * 
	 * @param taskNo
	 * @param businessType
	 * @return
	 * @throws BaseServiceException
	 * @throws BaseDaoException
	 */
	public Task setTaskBussinessType(String taskNo,String businessType) throws BaseServiceException, BaseDaoException{
		// 检查传递的参数是否有效
		if(null==taskNo||taskNo.equals("")){
			throw new BaseServiceException(CommonCodeConst.PROCESS_ERROR,"任务操作参数有错误");
		}
		
		Task task = taskDao.getTaskByTaskNoByJdbc(taskNo);
		
		if(null==task){
			throw new BaseServiceException(CommonCodeConst.PROCESS_ERROR,"任务操作参数有错误");
		}
		
		task.setBusinessType(businessType);
		
		return taskDao.updateTask(task);
		
	}
	
	/**
	 * 
	 * @param taskNo
	 * @param businessType
	 * @return
	 * @throws BaseServiceException
	 * @throws BaseDaoException
	 */
	public Task setTaskBussinessTypeByJdbc(String taskNo,String businessType) throws BaseServiceException, BaseDaoException{
		// 检查传递的参数是否有效
		if(null==taskNo||taskNo.equals("")){
			throw new BaseServiceException(CommonCodeConst.PROCESS_ERROR,"任务操作参数有错误");
		}
		int setResult = taskDao.setTaskBussinessTypeByJdbc(taskNo, businessType);
		Task task = null;
		if(setResult>0){
			task = taskDao.getTaskByTaskNoByJdbc(taskNo);
			// 设置自动发布抢单流程
		}
		return task;
	}
	
	/**
	 * 
	 * @param taskNo
	 * @return
	 * @throws BaseDaoException
	 * @throws BaseServiceException 
	 */
	public Task findTask(String taskNo) throws BaseDaoException, BaseServiceException{
		// 检查传递的参数是否有效
		if(null==taskNo||taskNo.equals("")){
			throw new BaseServiceException(CommonCodeConst.PROCESS_ERROR,"任务操作参数有错误");
		}
		return taskDao.findByTaskNo(taskNo);
	}
	
	
	public Task getTaskByJdbc(String taskNo) throws BaseDaoException, BaseServiceException{
		// 检查传递的参数是否有效
		if(null==taskNo||taskNo.equals("")){
			throw new BaseServiceException(CommonCodeConst.PROCESS_ERROR,"任务操作参数有错误");
		}		
		return taskDao.getTaskByTaskNoByJdbc(taskNo);
	}
	
	/**
	 * 
	 */
	public void deleteAllTask(){
		taskDao.deleteAllTask();
	}
	
	
}
