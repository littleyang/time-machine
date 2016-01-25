package com.vanke.status.machine.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.vanke.common.dao.base.JdbcBaseDao;
import com.vanke.status.machine.dao.crud.TaskStatusCrudDao;
import com.vanke.status.machine.model.TaskStatus;

@Repository
public class TaskStatusDao extends JdbcBaseDao {
	
	private static RowMapper<TaskStatus> taskStatusRowMapper = new RowMapper<TaskStatus>() {
		@Override
		public TaskStatus mapRow(ResultSet rs, int index)throws SQLException {
			TaskStatus status = new TaskStatus();
			status.setId(rs.getInt("id"));
			status.setName(rs.getString("name"));
			status.setOuterStatus(rs.getInt("outer_status"));
			status.setOuterName(rs.getString("outer_name"));
			status.setType(rs.getInt("type"));
			status.setCreated(rs.getDate("created"));
			status.setUpdated(rs.getDate("updated"));
			return status;
		}
	};
	
	@Autowired
	@Qualifier("taskStatusCrudDao")
	private TaskStatusCrudDao taskStatusCrudDao;
	
	/**
	 * 
	 * @param status
	 * @return
	 */
	public TaskStatus createTaskStatus(TaskStatus status){
		return taskStatusCrudDao.save(status);
	}
	
	/**
	 * 
	 * @param status
	 */
	public void deleteTaskStatus(TaskStatus status){
		taskStatusCrudDao.delete(status);
	}
	
	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TaskStatus> findAllByCrudDao(){
		return IteratorUtils.toList(taskStatusCrudDao.findAll().iterator());
	}
	
	/**
	 * 查询当前所有的任务状态总数
	 * @return
	 */
	public int getAllTaskStatusCountByJdbc(){
		StringBuilder sqlBuilder = new StringBuilder("select count(1) from task_status");
		Integer result = jdbcTemplate.queryForObject(sqlBuilder.toString(), null, Integer.class);
		return null==result?0:result;
	}
	
	/**
	 * 使用JDBC方法根据某个任务状态ID返回状态的对象
	 * @param id
	 * @return
	 */
	public TaskStatus getTaskStatusByIdByJdbc(int id){
		StringBuilder sqlBuilder = new StringBuilder("select * from task_status where id = ?");
		List<Object> params = new ArrayList<Object>();
		params.add(id);
		return jdbcTemplate.queryForObject(sqlBuilder.toString(), taskStatusRowMapper, params);
	}
	
	/**
	 * 使用JDBC方法根据某个任务状态status返回状态的对象
	 * @param code
	 * @return
	 */
	public TaskStatus getTaskStatusByStatusByJdbc(int status){
		StringBuilder sqlBuilder = new StringBuilder("select * from task_status where status = ?");
		List<Object> params = new ArrayList<Object>();
		params.add(status);
		return jdbcTemplate.queryForObject(sqlBuilder.toString(), taskStatusRowMapper, params);
	}
	
	/**
	 * 使用JDBC方法根据某个任务状态status返回状态的对象
	 * @param code
	 * @return
	 */
	public List<TaskStatus> getAllTaskStatusByJdbc(){
		StringBuilder sqlBuilder = new StringBuilder("select * from task_status");
		return jdbcTemplate.queryForList(sqlBuilder.toString(), null, TaskStatus.class);
	}

}
