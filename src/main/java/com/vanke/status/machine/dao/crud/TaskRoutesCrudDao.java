package com.vanke.status.machine.dao.crud;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vanke.status.machine.model.TaskRoutes;

@Repository("taskRoutesCrudDao")
public interface TaskRoutesCrudDao extends CrudRepository<TaskRoutes,Integer>{
	
	@Query("from TaskRoutes where id = :id")
	public TaskRoutes findById(@Param("id")int id);
	
	@Query("from TaskRoutes where bussiness_code = :bussiness_code "
			+ "and current_event = :current_event and current_status = :current_status")
	public TaskRoutes getCurrentTaskRoutes(@Param("bussiness_code")String bussinessCode,
			@Param("current_event")String currentEvent,@Param("current_status")int currentStatus);
	
	@Query("from TaskRoutes where bussiness_code = :bussiness_code "
			+ "and current_status = :current_status")
	public List<TaskRoutes> getNextRouteEvents(@Param("bussiness_code")String bussinessCode,
			@Param("current_status")int currentStatus);
	
	@Query("from TaskRoutes where bussiness_code = ?1")
	public List<TaskRoutes> findByBusinessCode(String bussiness_code);
	
}
