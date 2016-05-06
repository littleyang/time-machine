package com.vanke.common.task.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vanke.common.model.task.TaskLog;

@Repository("taskLogCrudDao")
public interface TaskLogCrudDao extends CrudRepository<TaskLog,Integer>{
	
	@Query("from TaskLog where id = :id")
	public TaskLog findById(@Param("id")int id);
	
	@Query("from TaskLog where task_no = :task_no ")
	public TaskLog getTaskLogByTaskNo(@Param("task_no")String taskNo);
	
	@Query("delete from TaskLog where id = ?1")
	public void deletedTaskLogById(@Param("id")int id);
	
	@Query("delete from TaskLog where task_no = :task_no")
	public void deletedTaskLogByTaskNo(@Param("task_no")int taskNo);
	
	@Query("from TaskLog where task_no = ?1")
	public List<TaskLog> getTaskLogsByTaskNo(String taskNo);

}
