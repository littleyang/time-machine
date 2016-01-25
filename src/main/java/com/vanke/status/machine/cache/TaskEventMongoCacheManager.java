package com.vanke.status.machine.cache;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.vanke.common.cache.mongo.MongoCommonManager;
import com.vanke.status.machine.model.TaskEvents;

@Repository
@Qualifier("taskEventMongoCacheManager")
public class TaskEventMongoCacheManager extends MongoCommonManager{
	
	@Override
	public TaskEvents findOneById(Integer id,String collection) {  
		 return mongoTemplate.findOne(new Query(Criteria.where("id").is(id)), TaskEvents.class, collection); 
	} 
	
	public TaskEvents findOneByCode(String code,String collection) {  
        return mongoTemplate.findOne(new Query(Criteria.where("code").is(code)), TaskEvents.class, collection);    
	}  
	
}


