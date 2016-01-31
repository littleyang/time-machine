package com.vanke.status.machine.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    public void createTask(){
    	log.info("zhuzher create task");
    }
    
    /**
     * zhuzher 任务评分
     */
    @RequestMapping(value = "/evaluate", method = RequestMethod.POST)
    @ResponseBody
    public void evalutionTask(){
    	
    }
    
    /**
     * zhuzher 催促任务
     */
    @RequestMapping(value = "/urge", method = RequestMethod.POST)
    @ResponseBody
    public void urgeTask(){
    	
    }
    
    /**
     * zhuzher 取消任务
     */
    @RequestMapping(value = "/cancle", method = RequestMethod.POST)
    @ResponseBody
    public void cancnelTask(){
    	
    }

}
