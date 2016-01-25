package com.vanke.status.machine.cache;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.vanke.common.cache.mongo.MongoCommonManager;

@Repository
@Qualifier("taskEventMongoCacheManager")
public class TaskEventMongoCacheManager extends MongoCommonManager{
	

}


