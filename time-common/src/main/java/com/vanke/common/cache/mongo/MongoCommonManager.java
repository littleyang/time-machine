package com.vanke.common.cache.mongo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.vanke.common.model.task.TaskLog;

@Repository("mongoCommonUtils")
public abstract class MongoCommonManager {
	
	@Autowired
	protected MongoTemplate mongoTemplate;
	
	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	/**
	 * insert object to collection
	 * @param value
	 * @param collection
	 */
	public void createValue(Object value, String collection){
		mongoTemplate.insert(value, collection);
	}
	
	/**
	 * 查找某一个对象
	 * @param params
	 * @param collectionName
	 * @return
	 */
	public Object findOneById(Integer id,String collection) {  
        return mongoTemplate.findOne(new Query(Criteria.where("id").is(id)), Object.class, collection);    
	}
	
	
	/**
	 *  update exist object
	 * @param value
	 * @param collection
	 * each object should implements
	 */
	public void updateOne(Object value,String collection) {  
       
    }  
	
	
	public void removeOneById(Integer id,String collection){
		mongoTemplate.remove(new Query(Criteria.where("id").is(id)), Object.class, collection); 
	}
	
	/**
	 * 
	 * @param dates: gte(great than and equal) and let( less than and equal)
	 * @param collection
	 * @return
	 */
	public List<TaskLog> getAllTaskLogBetweenDates(Map<String,Object> dates){
		Criteria queryCritera = Criteria.where("created").gte(dates.get("gte")).lte(dates.get("lte"));
		return mongoTemplate.find(new Query(queryCritera), TaskLog.class, "task_log");
	}
	

}
