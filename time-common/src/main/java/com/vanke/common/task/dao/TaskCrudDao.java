package com.vanke.common.task.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vanke.common.model.task.Task;

@Repository("taskCrudDao")
public interface TaskCrudDao extends CrudRepository<Task,Integer>{
	
	@Query("from Task where id = ?1")
	public Task findById(int id);
	
	@Query("from Task where task_no = ?1")
	public Task findByTaskNo(String taskNo);
	
	@Query("delete from Task where id = ?1")
	public void deleteTaskById(int id);
	
	@Query("delete from Task where task_no = ?1")
	public void deleteTaskByTaskNo(String taskNo);
	
//	@Query("truncate table tasks")
//	public void truncateTasks();

}
