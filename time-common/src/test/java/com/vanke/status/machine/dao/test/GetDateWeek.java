package com.vanke.status.machine.dao.test;

import java.util.Calendar;
import java.util.Date;

public class GetDateWeek {
	
	public static void main(String[] args){
		
		Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);

        calendar.setTime(new Date());
        
        System.out.println(calendar.get(Calendar.WEEK_OF_YEAR));
   
	}

}
