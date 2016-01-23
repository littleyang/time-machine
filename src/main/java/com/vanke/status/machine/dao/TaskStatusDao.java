package com.vanke.status.machine.dao;

import org.springframework.stereotype.Repository;

import com.vanke.status.machine.dao.base.JdbcBaseDao;

@Repository
public class TaskStatusDao extends JdbcBaseDao {
	
	public int getAllStatusCount(){
		
		return jdbcTemplate.queryForList("select * from task_status").size();
		
	}


}
