package com.vanke.status.machine.dao.crud;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vanke.status.machine.model.TaskStatus;

@Repository("taskStatusCrudDao")
public interface TaskStatusCrudDao extends CrudRepository<TaskStatus,Integer>{
	
	@Query("from TaskStatus where id = :id")
	public TaskStatus findById(@Param("id")int id);
	
	@Query("from TaskStatus")
	public int getAllTaskStatusCount();
}
