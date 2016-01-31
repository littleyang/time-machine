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
    public void createTask(){
    	log.info("zhuzher create task");
    }
    
    /**
     * lebang 处理任务
     */
    @RequestMapping(value = "/deal", method = RequestMethod.POST)
    @ResponseBody
    public void operation(){
    	
    }
    
    /**
     * 设置业务类型
     */
    @RequestMapping(value = "/bussiness/set", method = RequestMethod.POST)
    @ResponseBody
    public void setTaskBussiness(){
    	
    }
    
    

}
