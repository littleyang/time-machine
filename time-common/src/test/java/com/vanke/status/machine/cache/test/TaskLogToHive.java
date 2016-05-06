package com.vanke.status.machine.cache.test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.vanke.common.exceptions.BaseDaoException;
import com.vanke.common.model.task.TaskLog;
import com.vanke.common.task.dao.TaskLogDao;
import com.vanke.status.machine.cache.TaskEventMongoCacheManager;
import com.vanke.test.base.BaseTestUnit;

public class TaskLogToHive extends BaseTestUnit{
	
	@Autowired
	private TaskEventMongoCacheManager taskEventMongoCacheManager;
	
	@Autowired
	private TaskLogDao taskLogDao;

	
	@Test
	public void testGetTaskEventsByIdFromMongo() throws ParseException, BaseDaoException{
		
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
			System.out.println("log id : " + taskLog.getObjectId());
			System.out.println("log status : " + taskLog.getStatus());
			System.out.println("log taskNo : " + taskLog.getTaskNo());
			System.out.println("log msg : " + taskLog.getMsg());
			taskLogDao.createTaskLog(taskLog);
		}	
		
//		MongoClient mongoClient = new MongoClient("10.0.72.97");
//		DB db = mongoClient.getDB( "falcon" );
//		DBCollection coll = db.getCollection("task_log");
//		
//		BasicDBObject query = new BasicDBObject("created", new BasicDBObject("$gte", endDate));
//		
//		DBCursor cursor = coll.find(query);
//		
//		try {
//			while (cursor.hasNext()) {
//				System.out.println(cursor.next());
//			}
//		} finally {
//			cursor.close();
//		}
//		
		
	}
	
	@Test
	public void testTaskLogCreate() throws BaseDaoException{
		TaskLog log = new TaskLog();
		log.setObjectId(123);
		log.setEvent("test");
		log.setCreated(new Date());
		log.setMsg("test");
		log.setRate(1);
		log.setStatus(1000);
		log.setSourceId(10000);
		log.setScore("6");
		log.setTaskNo("yyyyyyyssssss");
		taskLogDao.createTaskLog(log);
	}
	

}
