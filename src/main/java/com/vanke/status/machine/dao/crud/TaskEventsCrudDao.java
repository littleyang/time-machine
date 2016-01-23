package com.vanke.status.machine.dao.crud;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vanke.status.machine.model.TaskEvents;

@Repository
public interface TaskEventsCrudDao extends CrudRepository<TaskEvents,Integer>{
	
	@Query("select * from TaskEvent where id = :id")
	public TaskEvents findById(@Param("id")int id);
	
	@Query("select count(1) from TaskEvent")
	public int getAllTaskEventsCount();
}
