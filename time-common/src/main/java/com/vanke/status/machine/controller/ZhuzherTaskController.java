package com.vanke.status.machine.controller;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vanke.common.constant.CommonCodeConst;
import com.vanke.common.data.vo.TaskSnapshot;
import com.vanke.common.exceptions.BaseDaoException;
import com.vanke.common.exceptions.BaseServiceException;
import com.vanke.common.model.task.Task;
import com.vanke.common.service.TaskService;
import com.vanke.status.machine.service.TaskStatusMachineService;

@Controller
@RequestMapping("/api/zhuzher/task")
public class ZhuzherTaskController {
	
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
     * zhuzher 创建任务
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String,Object>> createTask(@RequestBody String taskParams){
    	
    	long start = System.currentTimeMillis();
    	
    	JSONObject objectParams = JSONObject.fromObject(taskParams);
    	
    	System.out.println("task business is   " + objectParams.getString("bussiness_type"));
    	
    	Task task = new Task();
    	task.setBusinessType(objectParams.getString("bussiness_type"));
    	
    	// 初始化 task no
    	task.setTaskNo(taskService.createTaskNo());
    	// 初始化状态
    	task.setStatus(999);
    	
    	String taskInitEvents = "E100001";
    	
    	TaskSnapshot taskData = null;
    	
    	Map<String,Object> result = new HashMap<String,Object>();
    	
    	HttpStatus status = HttpStatus.OK;
    	
    	try {
    		Task create = taskService.createTask(task);
    		
			taskData = taskStatusMachineService.operationTask(create, taskInitEvents, CommonCodeConst.TASK_EVENT_TYPE_ZHUZHER);
			
			//taskData = taskStatusMachineService.zhuzherCreateTask(create, CommonCodeConst.TASK_EVENT_TYPE_ZHUZHER);
			
			result.put("code", CommonCodeConst.STATUS_OK);
			result.put("msg", CommonCodeConst.SUCCESS_MESSAGE);
			result.put("data", taskData);
			
		} catch (BaseServiceException e) {
			// TODO Auto-generated catch block
			result.put("code", e.getCode());
			result.put("msg", e.getMsg());
			result.put("data", null);
			status = HttpStatus.BAD_REQUEST;
		} catch (BaseDaoException e) {
			// TODO Auto-generated catch block
			result.put("code", e.getCode());
			result.put("msg", e.getMsg());
			result.put("data", null);
			status = HttpStatus.BAD_REQUEST;
		}
    	
    	long end = System.currentTimeMillis();
    	log.info("/api/zhuzher/task/create 耗时:  " + (end - start) + " ms");

    	return new ResponseEntity<Map<String,Object>>(result, status);
    }
    
    /**
     * zhuzher 获取task详情，包括操作
     */
    @RequestMapping(value = "/{task_no}/detail", method = RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> getTaskDetail(@PathVariable("task_no") String taskNo){
    	
    	TaskSnapshot taskData = null;
    	
    	Map<String,Object> result = new HashMap<String,Object>();
    	
    	HttpStatus status = HttpStatus.OK;
    	
    	try {
    		taskData = taskService.getTaskDetail(taskNo, CommonCodeConst.TASK_EVENT_TYPE_ZHUZHER);
			result.put("code", CommonCodeConst.STATUS_OK);
			result.put("msg", CommonCodeConst.SUCCESS_MESSAGE);
			result.put("data", taskData);
			
		} catch (BaseServiceException e) {
			// TODO Auto-generated catch block
			result.put("code", e.getCode());
			result.put("msg", e.getMsg());
			result.put("data", null);
			status = HttpStatus.BAD_REQUEST;
		} catch (BaseDaoException e) {
			// TODO Auto-generated catch block
			result.put("code", e.getCode());
			result.put("msg", e.getMsg());
			result.put("data", null);
			status = HttpStatus.BAD_REQUEST;
		}
    	
    	return new ResponseEntity<Map<String,Object>>(result, status);
    }
    
    
    /**
     * zhuzher 处理任务
     */
    @RequestMapping(value = "/deal/{task_no}", method=RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> operation(@PathVariable("task_no") String taskNo, 
    		@RequestParam("operation") String operation){
    	
    	System.out.println("task_no   is   " + taskNo);
    	System.out.println("operation   is   " + operation);
    	
    	Map<String,Object> result = new HashMap<String,Object>();
    	
    	HttpStatus status = HttpStatus.OK;
    	
    	TaskSnapshot taskData = null;
    	
    	try {
    		Task task = taskService.getTaskByJdbc(taskNo);
    		// operation task
    		taskData = taskStatusMachineService.operationTask(task, operation, CommonCodeConst.TASK_EVENT_TYPE_LEBANG);
			
    		result.put("code", CommonCodeConst.STATUS_OK);
			result.put("msg", CommonCodeConst.SUCCESS_MESSAGE);
			result.put("data", taskData);
			
		} catch (BaseServiceException e) {
			// TODO Auto-generated catch block
			result.put("code", e.getCode());
			result.put("msg", e.getMsg());
			status = HttpStatus.BAD_REQUEST;
		} catch (BaseDaoException e) {
			// TODO Auto-generated catch block
			result.put("code", e.getCode());
			result.put("msg", e.getMsg());
			status = HttpStatus.BAD_REQUEST;
		}
    	
    	return new ResponseEntity<Map<String,Object>>(result, status);
    	
    }

}
