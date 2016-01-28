package com.vanke.status.machine.service;

import org.springframework.stereotype.Service;

@Service("taskStatusService")
public class TaskStatusService {
	
	
	public int getRouteNextStatusByTypeLastStatusEvent(String type, int lastSatus, String event){
	
		if(null==type||type.equals("")){
			return 1;
		}
		
		if(lastSatus==0){
			return 2;
		}
		
		if(event.equals("")){
			return 3;
		}
		
		return 0;
		
	}

}
