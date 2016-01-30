package com.vanke.status.machine.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.vanke.common.constant.ResponesCodeConst;
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

@Service("taskStatusMachineService")
public class TaskStatusMachineService {
	
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
	
	
	/**
	 * 根据当前任务的业务类型以及当前的状态查找并流转路由规则
	 * @param task
	 * @param operation
	 * @return
	 * @throws BaseServiceException
	 * @throws BaseDaoException
	 */
	public TaskSnapshot operationTask(Task task,String operation) throws BaseServiceException, BaseDaoException{
		if(null==task){
			throw new BaseServiceException(ResponesCodeConst.PROCESS_ERROR,"任务不能为空");
		}
		
		if(null==operation||operation.equals("")){
			throw new BaseServiceException(ResponesCodeConst.PROCESS_ERROR,"任务操作不能为空");
		}
		
		// 获取当前任务需要遵循的路由规则
		TaskRoutes route = taskRoutesDao.getCurrentRoutesByJdbc(task.getBusinessType(), operation, task.getStatus());
		
		if(null==route||route.getCurrentStatus()==0||route.getCurrentEvent().equals("")){
			throw new BaseServiceException(ResponesCodeConst.PROCESS_ERROR,"任务操作错了,无法找到对应状态的下一步操作");
		}
		
		// 对任务执行某个事件过后，查找修改过后的Task的下一系列的操作事件
		//List<TaskRoutes> taskNextEventRoutes = taskRoutesDao.findNextTaskRouteEvents(savedTask.getBusinessType(), savedTask.getStatus());
		List<TaskRoutes> taskNextEventRoutes = taskRoutesDao.findNextTaskRouteEvents(task.getBusinessType(), task.getStatus());
				
		if(null==taskNextEventRoutes||taskNextEventRoutes.size()==0){
			throw new BaseServiceException(ResponesCodeConst.PROCESS_ERROR,"任务操作错了,无法找到对应状态的下一步操作");
		}
		
		// 处理task业务逻辑,设置当前操作对任务改变的下一个状态并保存
		task.setStatus(route.getNextStatus());
		Task savedTask = taskDao.updateTask(task);
		
		//查找下一个操作事件
		List<TaskEvents> nextEvents = new ArrayList<TaskEvents>();
		
		for(int i=0;i<taskNextEventRoutes.size();i++){
			TaskRoutes temp = taskNextEventRoutes.get(i);
			TaskEvents eventTemp = taskEventsDao.getTaskByCrudDaoByCode(temp.getNextEvent());
			nextEvents.add(eventTemp);
		}
		
		TaskSnapshot result = new TaskSnapshot();
		
		result.setTask(savedTask);
		
		result.setOperations(nextEvents);
		
		return result;
	}


}
