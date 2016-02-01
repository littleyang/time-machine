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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
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
    public void createTask(@RequestBody Task task){
    	log.info("zhuzher create task and params :  " + task);
    	long start = System.currentTimeMillis();
    	
    	//Task task = mapper.readValue(requestBody, Task.class);
    	
    	long end = System.currentTimeMillis();
    	log.info("/api/lebang/task/create 耗时:  " + (end - start) + " ms");
    	
    }
    
    /**
     * lebang 处理任务
     */
    @RequestMapping(value = "/deal/{task_no}", method=RequestMethod.PUT)
    public void operation(@PathVariable("task_no") String taskNo,@RequestBody String operation){
    	
    }
    
    /**
     * 设置业务类型
     */
    @RequestMapping(value = "/{task_no}/set/business", method = RequestMethod.PUT)
    public void setTaskBussiness(@PathVariable("task_no") String taskNo, @RequestBody String bussinessType){
    	
    }
    
    
    /**
     * 获取task详情，包括操作
     */
    @RequestMapping(value = "/{task_no}/detail", method = RequestMethod.GET)
    public void getTaskDetail(@PathVariable("task_no") String taskNo){
    	
    }
    
    

}
