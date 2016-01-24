package com.vanke.status.machine.cache;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.vanke.common.cache.redis.RedisCacheCommonManager;

@Repository
@Qualifier("taskEventRedisCacheManager")
public class TaskEventRedisCacheManager extends RedisCacheCommonManager{
	
	

}
