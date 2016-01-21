package com.vanke.status.machine.dao;

import org.springframework.stereotype.Repository;

import com.vanke.status.machine.dao.base.BaseDao;

@Repository
public class StatusEventsDao extends BaseDao {
	
	public int getAllStatusEventsCount(){
		
		return jdbcTemplate.queryForList("select * from task_events").size();
		
	}


}
