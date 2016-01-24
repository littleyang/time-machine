package com.vanke.status.machine.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.vanke.status.machine.dao.base.JdbcBaseDao;
import com.vanke.status.machine.dao.crud.TaskEventsCrudDao;

@Component("taskEventsDao")
public class TaskEventsDao extends JdbcBaseDao {
	
	@Autowired
	@Qualifier("taskEventsCrudDao")
	private TaskEventsCrudDao taskEventsCrudDao;
	
	public int getAllEventsCountByJdbc(){
		
		return jdbcTemplate.queryForList("select * from task_events").size();
	}
	
	public int getAllEventsCountByCrud(){
		return taskEventsCrudDao.getAllTaskEventsCount();
	}
}
