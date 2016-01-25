package com.vanke.status.machine.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.vanke.common.dao.base.JdbcBaseDao;
import com.vanke.status.machine.dao.crud.TaskBusinessCrudDao;

@Repository
@Qualifier("taskBusinessDao")
public class TaskBusinessDao extends JdbcBaseDao {
	
	@Autowired
	@Qualifier("taskBusinessCrudDao")
	private TaskBusinessCrudDao taskBusinessCrudDao;
	
	public int getAllBussinessCountByJdbc(){
		
		return jdbcTemplate.queryForList("select * from business_type").size();
		
	}


}
