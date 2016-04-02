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
	
	@Query("from TaskStatus where status = ?1")
	public TaskStatus findByStatus(int status);
	
	@Query("delete from TaskStatus where status = ?1")
	public TaskStatus deletdByStatus(int status);
	
	@Query("delete from TaskStatus where id = ?1")
	public TaskStatus deletdById(int id);
	
	
	
}
