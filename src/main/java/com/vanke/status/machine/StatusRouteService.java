package com.vanke.status.machine;

import org.springframework.stereotype.Service;

@Service
public class StatusRouteService {
	
	
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
