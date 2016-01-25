package com.vanke.status.machine.cache;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.vanke.common.cache.mongo.MongoCommonManager;
import com.vanke.status.machine.model.TaskEvents;

@Repository
@Qualifier("taskEventMongoCacheManager")
public class TaskEventMongoCacheManager extends MongoCommonManager{
	
	private final String TASK_EVENT_DEAULT_MONGO_COLLECTION = "task_events";
	
	/**
	 * 
	 * @param event
	 * @return
	 */
	public TaskEvents create(TaskEvents event){
		super.createValue(event, TASK_EVENT_DEAULT_MONGO_COLLECTION);
		return event;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public TaskEvents findOneById(Integer id) {
		
		return mongoTemplate.findOne(new Query(Criteria.where("id").is(id)), TaskEvents.class, TASK_EVENT_DEAULT_MONGO_COLLECTION); 
	} 
	
	/**
	 * 
	 * @param code
	 * @return
	 */
	public TaskEvents findOneByCode(String code) {  
        return mongoTemplate.findOne(new Query(Criteria.where("code").is(code)), TaskEvents.class, TASK_EVENT_DEAULT_MONGO_COLLECTION);    
	}
	
	/**
	 * 
	 * @param event
	 */
	public void updateOne(TaskEvents event){
		DBObject dbDoc = new BasicDBObject();
		mongoTemplate.getConverter().write(event, dbDoc);
		Update update = Update.fromDBObject(dbDoc);
		mongoTemplate.upsert(new Query(Criteria.where("id").is(event.getId())), update, TASK_EVENT_DEAULT_MONGO_COLLECTION);
	}
	
	/**
	 * 
	 * @param event
	 */
	public void removeOne(TaskEvents event){
		super.removeOneById(event.getId(), TASK_EVENT_DEAULT_MONGO_COLLECTION);
	}
	
}


