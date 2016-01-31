package com.vanke.status.machine.service;

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
	public TaskSnapshot operationTask(Task task,String operation,int operationType) throws BaseServiceException, BaseDaoException{
		
		// 检查传递的参数是否有效
		if(null==task||null==operation||operation.equals("")||0==operationType){
			throw new BaseServiceException(ResponesCodeConst.PROCESS_ERROR,"任务操作参数有错误");
		}
		
		// 获取当前任务需要遵循的路由规则
		TaskRoutes route = taskRoutesDao.getCurrentRoutesByJdbc(task.getBusinessType(), operation, task.getStatus(), operationType);
		
		if(null==route||route.getCurrentStatus()==0||route.getCurrentEvent().equals("")){
			// 如果上述查找任何一个失败或者是没有返回值，都表示任务的路由规则或者是对应的事件操作失败
			throw new BaseServiceException(ResponesCodeConst.PROCESS_ERROR,"任务操作错了,无法找到对应状态的下一步操作");
		}
		
		// 对任务执行某个事件过后，查找修改过后的Task的下一系列的操作事件
		//List<TaskRoutes> taskNextEventRoutes = taskRoutesDao.findNextTaskRouteEvents(savedTask.getBusinessType(), savedTask.getStatus());
		//List<TaskRoutes> taskNextEventRoutes = taskRoutesDao.findNextTaskRouteEvents(task.getBusinessType(), task.getStatus(), operationType);
		List<TaskRoutes> taskNextEventRoutes = taskRoutesDao.findNextTaskRouteEvents(task.getBusinessType(), route.getNextStatus(), operationType);
						
		// 查找下一个操作事件
		List<TaskEvents> nextEvents = taskEventsDao.findNextEventsByRoutes(taskNextEventRoutes);
		
		if(null==route||route.getCurrentStatus()==0||route.getCurrentEvent().equals("")||null==taskNextEventRoutes
				||taskNextEventRoutes.size()==0||null==nextEvents||nextEvents.size()==0){
			// 如果上述查找任何一个失败或者是没有返回值，都表示任务的路由规则或者是对应的事件操作失败
			throw new BaseServiceException(ResponesCodeConst.PROCESS_ERROR,"任务操作错了,无法找到对应状态的下一步操作");
		}
			
		// 处理task业务逻辑,设置当前操作对任务改变的下一个状态并保存
		task.setStatus(route.getNextStatus());
		Task savedTask = taskDao.updateTask(task);
		
		
		// 设置操作过后的返回数据
		TaskSnapshot result = new TaskSnapshot();
		
		result.setTask(savedTask);
		
		result.setOperations(nextEvents);
		
		return result;
	}


}
