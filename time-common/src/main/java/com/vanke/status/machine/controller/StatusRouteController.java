package com.vanke.status.machine.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vanke.status.machine.service.StatusRouteService;

@Controller
@RequestMapping("/task/machine")
public class StatusRouteController {
	
	private static final Log log = LogFactory.getLog(StatusRouteController.class);
    @SuppressWarnings("unused")
	private final ObjectMapper mapper = new ObjectMapper();
    
    @Autowired
    private StatusRouteService routeService;
    
    @RequestMapping(value = "/status/next", method = RequestMethod.POST)
    @ResponseBody
    public int findNextStatus(
    		@RequestParam("business_type") String businessType,
            @RequestParam("current_status") int currentStatus,
            @RequestParam("event") String event){
    	
    	int next_status = routeService.getRouteNextStatusByTypeLastStatusEvent(businessType, currentStatus, event);
    	log.info("findNextStatus   " + next_status);
    	return next_status;
    }


}
