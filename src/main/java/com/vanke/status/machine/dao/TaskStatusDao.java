package com.vanke.status.machine.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.vanke.status.machine.dao.base.JdbcBaseDao;
import com.vanke.status.machine.dao.crud.TaskStatusCrudDao;

@Repository
public class TaskStatusDao extends JdbcBaseDao {
	
	@Autowired
	@Qualifier("taskStatusCrudDao")
	private TaskStatusCrudDao taskStatusCrudDao;
	
	public int getAllStatusCountByJdbc(){
		
		return jdbcTemplate.queryForList("select * from task_status").size();
		
	}


}
