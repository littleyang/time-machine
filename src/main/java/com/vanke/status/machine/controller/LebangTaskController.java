package com.vanke.status.machine.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vanke.common.constant.ResponesCodeConst;
import com.vanke.common.data.vo.TaskSnapshot;
import com.vanke.common.exceptions.BaseDaoException;
import com.vanke.common.exceptions.BaseServiceException;
import com.vanke.common.model.task.Task;
import com.vanke.common.service.TaskService;
import com.vanke.status.machine.service.TaskStatusMachineService;

@Controller
@RequestMapping("/api/lebang/task")
public class LebangTaskController {
	
	private static final Log log = LogFactory.getLog(StatusRouteController.class);
    @SuppressWarnings("unused")
	private final ObjectMapper mapper = new ObjectMapper();
    
    @Autowired
   	@Qualifier("taskService")
    private TaskService taskService;
    
    @Autowired
	@Qualifier("taskStatusMachineService")
	private TaskStatusMachineService taskStatusMachineService;
    
    /**
     * lebang 创建任务
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public TaskSnapshot createTask(@RequestBody Task task){
    	//log.info("zhuzher create task and params :  " + task);
    	long start = System.currentTimeMillis();
    	
    	System.out.println("task business is   " + task.getBusinessType());
    	
    	// 初始化 task no
    	task.setTaskNo(taskService.createTaskNo());
    	// 初始化状态
    	task.setStatus(1000);
    	
    	String taskInitEvents = "E100001";
    	
    	TaskSnapshot taskData = null;
    	
    	try {
    		Task create = taskService.createTask(task);
    		
			taskData = taskStatusMachineService.operationTask(create, taskInitEvents, ResponesCodeConst.TASK_EVENT_TYPE_LEBANG);
			
		} catch (BaseServiceException | BaseDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	long end = System.currentTimeMillis();
    	log.info("/api/lebang/task/create 耗时:  " + (end - start) + " ms");

    	return taskData;
    	
    }
    
    /**
     * lebang 处理任务
     */
    @RequestMapping(value = "/deal/{task_no}", method=RequestMethod.POST, produces={"application/json"})
    public void operation(@PathVariable("task_no") String taskNo, @RequestParam String operation){
    	
    }
    
    /**
     * 设置业务类型
     */
    @RequestMapping(value = "/set/business/{task_no}", method = RequestMethod.POST,produces={"application/json"})
    public void setTaskBussiness(@PathVariable("task_no") String taskNo, @RequestParam String bussinessType){
    	
    }
    
    
    /**
     * 获取task详情，包括操作
     */
    @RequestMapping(value = "/{task_no}/detail", method = RequestMethod.GET)
    public void getTaskDetail(@PathVariable("task_no") String taskNo){
    	
    }
    
    

}
