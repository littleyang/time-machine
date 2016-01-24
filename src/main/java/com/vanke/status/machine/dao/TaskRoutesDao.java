package com.vanke.status.machine.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.vanke.status.machine.dao.base.JdbcBaseDao;
import com.vanke.status.machine.dao.crud.TaskRoutesCrudDao;

@Repository
public class TaskRoutesDao extends JdbcBaseDao {
	
	@Autowired
	@Qualifier("taskRoutesCrudDao")
	private TaskRoutesCrudDao taskRoutesCrudDao;
	
	public int getAllRoutesCountByJdbc(){
		
		return jdbcTemplate.queryForList("select * from task_routes").size();
		
	}


}
