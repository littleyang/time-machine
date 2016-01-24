package com.vanke.status.machine.dao.crud;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vanke.status.machine.model.TaskRoutes;

@Repository("taskRoutesCrudDao")
public interface TaskRoutesCrudDao extends CrudRepository<TaskRoutes,Integer>{
	
	@Query("from TaskRoutes where id = :id")
	public TaskRoutes findById(@Param("id")int id);
	
	@Query("from TaskRoutes")
	public int getAllTaskRoutsCount();
}
