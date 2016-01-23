package com.vanke.status.machine.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vanke.status.machine.dao.base.JdbcBaseDao;
import com.vanke.status.machine.dao.crud.TaskEventsCrudDao;

@Component("taskEventsDao")
public class TaskEventsDao extends JdbcBaseDao {
	
	@Autowired
	private TaskEventsCrudDao eventsCrudDao;
	
	public int getAllEventsCount(){
		
		return jdbcTemplate.queryForList("select * from task_events").size();
	}
	
	public int getAllCount(){
		return eventsCrudDao.getAllTaskEventsCount();
	}
}
