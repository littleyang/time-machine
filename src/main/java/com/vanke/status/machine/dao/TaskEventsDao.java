package com.vanke.status.machine.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.vanke.status.machine.dao.base.JdbcBaseDao;
import com.vanke.status.machine.dao.crud.TaskEventsCrudDao;
import com.vanke.status.machine.model.TaskEvents;

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

	public TaskEvents getTaskByCrudDaoById(int id) {
		// TODO Auto-generated method stub
		return taskEventsCrudDao.findById(id);
	}
	
	public TaskEvents getTaskByCrudDaoByCode(String code) {
		// TODO Auto-generated method stub
		return taskEventsCrudDao.findByCode(code);
	}

	public TaskEvents createTaskEvent(TaskEvents taskEvent) {
		// TODO Auto-generated method stub
		return taskEventsCrudDao.save(taskEvent);
	}
	
}
