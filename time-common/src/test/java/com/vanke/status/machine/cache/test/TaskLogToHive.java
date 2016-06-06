package com.vanke.status.machine.cache.test;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.time.util.TimeDateUtil;
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
	public void testGetYesterDayTaskLogsFromMongo() throws ParseException, BaseDaoException{
				
		Map<String,Object> dates = new HashMap<String,Object>();
		
		String beginDateString = "2016-05-24 00:00:00";
		
		String endDateString = "2016-05-28 00:00:00";
		
		Date today = new Date();
		
		// yesterday 00:00:00
		//Date beginDate = TimeDateUtil.getSpecifiedDayZeroHourBefore(today);
		Date beginDate = TimeDateUtil.parse(beginDateString);
		
		// today 00:00:00
		//Date endDate = TimeDateUtil.getSpecifiedDay24HourBefore(today);
		
		Date endDate = TimeDateUtil.parse(endDateString);
		
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
			System.out.println("log score : " + taskLog.getScore());
			taskLogDao.createTaskLog(taskLog);
		}	
	}
	
	
	@Test
	public void testGetBeforeDate() throws ParseException{
		Date today = new Date();
		
		Date yesterdayZero = TimeDateUtil.getSpecifiedDayZeroHourBefore(today);
		
		Date yesterday24 = TimeDateUtil.getSpecifiedDay24HourBefore(today);
		
		System.out.println(yesterdayZero);
		
		System.out.println(yesterday24);
	}
	
	@Test
	public void testTaskLogFromMongoToHiveOneTime() throws ParseException, BaseDaoException{
		
		MongoClient mongoClient = new MongoClient("10.0.72.97");
		DB db = mongoClient.getDB( "falcon" );
		DBCollection coll = db.getCollection("task_log");
		
		DBCursor cursor = coll.find();
		 cursor.addOption(com.mongodb.Bytes.QUERYOPTION_NOTIMEOUT);
		
		try {
			while (cursor.hasNext()) {
				
				TaskLog log = new TaskLog();
				
				DBObject myObj = cursor.next();
				
				//System.out.println(myObj);
								
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				
				String json = gson.toJson(myObj);
				//System.out.println(json);
			
				JSONObject object = JSONObject.fromObject(json);
				//System.out.println(object.toString());
				
				//System.out.println(object.getString("task_no"));
				
				if(object.has("objectId")){
					// set object id
					log.setObjectId(object.getInt("objectId"));
				}
				
				
				if(object.has("status")){
					// set status
					log.setStatus(object.getInt("status"));
				}
				
				if(object.has("created")){
					String dateString = object.getString("created");
					Date createdTemp = null;
					if(dateString.contains(",")){
						// 特殊的日期格式
						createdTemp = TimeDateUtil.parseSpecialFormat(object.getString("created"));
					}else{
						createdTemp = TimeDateUtil.parse(object.getString("created"));
					}
					String createdString = TimeDateUtil.format(createdTemp);
					Date created = TimeDateUtil.parse(createdString);
					//System.out.println(created);
					//设置时间
					log.setCreated(created);
				}
				
				if(object.has("source_id")){
					// set source_id
					log.setSourceId(object.getInt("source_id"));
				}
				
				if(object.has("rate")){
					// set rate
					log.setRate(object.getInt("rate"));
				}
			
				if(object.has("task_no")){
					//设置task no
					//System.out.println(object.getString("task_no"));
					log.setTaskNo(object.get("task_no").toString());
					//System.out.println(log.getTaskNo());
				}
				
				if(object.has("event")){
					//设置task no
					log.setEvent(object.getString("event"));
				}
				
				if(object.has("score")){
					//设置task no
					log.setScore(object.getString("score"));
				}
				
				if(object.has("msg")){
					//设置task no
					log.setMsg(object.getString("msg"));
				}
				// 写入到数据库
				taskLogDao.createTaskLog(log);
			}
		} finally {
			cursor.close();
		}
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
