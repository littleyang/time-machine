package com.vanke.status.machine.dao;

import org.springframework.stereotype.Repository;

import com.vanke.status.machine.dao.base.BaseDao;

@Repository
public class TaskEventsDao extends BaseDao {
	
	public int getAllEventsCount(){
		
		return jdbcTemplate.queryForList("select * from task_events").size();
		
	}


}
