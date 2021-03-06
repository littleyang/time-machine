package com.hadoop.test.hive;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hadoop.test.base.BaseTestUnit;
import com.vanke.common.model.task.TaskLog;
import com.vanke.status.machine.cache.TaskEventMongoCacheManager;

public class TaskLogToHive extends BaseTestUnit{
	
	@Autowired
	private TaskEventMongoCacheManager taskEventMongoCacheManager;

	
	@Test
	public void testGetTaskEventsByIdFromMongo() throws ParseException{
		
		Map<String,Object> dates = new HashMap<String,Object>();
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String beginDateString = "2016-05-05 00:00:00";
		String endDateString  = "2016-05-05 23:59:59";
		
		Date beginDate = df.parse(beginDateString);
		
		Date endDate = df.parse(endDateString);
		
		dates.put("gte", beginDate);
		dates.put("lte", endDate);
		
		List<TaskLog> logs = taskEventMongoCacheManager.getAllTaskLogBetweenDates(dates);
		
		System.out.println(logs.size());
		
		for(int i=0;i<logs.size();i++){
			TaskLog taskLog = logs.get(i);
			System.out.println("log status : " + taskLog.getStatus());
			System.out.println("log taskNo : " + taskLog.getTaskNo());
			System.out.println("log msg : " + taskLog.getMsg());
		}
		
	}
	

}
