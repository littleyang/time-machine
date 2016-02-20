package com.vanke.status.machine.dao.crud;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vanke.status.machine.model.TaskEvents;

@Repository("taskEventsCrudDao")
public interface TaskEventsCrudDao extends CrudRepository<TaskEvents,Integer>{
	
	@Query("from TaskEvents where id = :id")
	public TaskEvents findById(@Param("id")int id);
	
	@Query("from TaskEvents where code = :code")
	public TaskEvents findByCode(@Param("code")String code);
	
	@Query("from TaskEvents")
	public int getAllTaskEventsCount();
	
	@Query("delete from TaskEvents where id = ?1")
	public void deletedTaskEvent(@Param("id")int id);
	
	@Query("from TaskEvents where code = :code and type = :type")
	public TaskEvents findByCodeAndType(@Param("code")String code,@Param("type")int type);
}
