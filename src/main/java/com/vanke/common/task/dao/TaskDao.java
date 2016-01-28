package com.vanke.common.task.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.vanke.common.constant.ResponesCodeConst;
import com.vanke.common.dao.base.JdbcBaseDao;
import com.vanke.common.exceptions.BaseDaoException;
import com.vanke.common.model.task.Task;

@Repository
@Qualifier("taskDao")
public class TaskDao extends JdbcBaseDao{
	
	private static RowMapper<Task> taskRowMapper = new RowMapper<Task>() {
		@Override
		public Task mapRow(ResultSet rs, int index)throws SQLException {
			Task task = new Task();
			task.setId(rs.getInt("id"));
			task.setTaskNo(rs.getString("task_no"));
			task.setBusinessType(rs.getString("business_type"));
			task.setStatus(rs.getInt("status"));
			return task;
		}
	};
	
	@Autowired
	@Qualifier("taskCrudDao")
	private TaskCrudDao taskCrudDao;
	
	/**
	 * 
	 * @param task
	 * @return
	 * @throws BaseDaoException 
	 */
	public Task createTask(Task task) throws BaseDaoException{
		if(null==task||task.getTaskNo().equals("")){
			throw new BaseDaoException(ResponesCodeConst.QUERY_PARAMS_ERROR_CODE,"创建任务参数错误，缺少参数值");
		}
		return taskCrudDao.save(task);
	}
	
	/**
	 * 
	 * @param task
	 * @throws BaseDaoException 
	 */
	public void deleteTask(Task task) throws BaseDaoException{
		if(null==task||task.getTaskNo().equals("")){
			throw new BaseDaoException(ResponesCodeConst.QUERY_PARAMS_ERROR_CODE,"删除任务参数错误，缺少参数值");
		}
		taskCrudDao.delete(task);
	}
	
	/**
	 * 
	 * @param taskId
	 */
	public void deleteTaskById(int taskId){
		taskCrudDao.deleteTaskById(taskId);
	}
	
	/**
	 * 
	 * @param taskNo
	 */
	public void deleteTaskByTaskNo(String taskNo){
		taskCrudDao.deleteTaskByTaskNo(taskNo);
	}
	
	/**
	 * 
	 */
	public void deleteAllTask(){
		taskCrudDao.deleteAll();
	}
	
	/**
	 * 
	 * @param taskId
	 * @return
	 * @throws BaseDaoException 
	 */
	public Task findById(int taskId) throws BaseDaoException{
		if(taskId==0){
			throw new BaseDaoException(ResponesCodeConst.QUERY_PARAMS_ERROR_CODE,"查询任务参数错误，缺少参数值");
		}
		return taskCrudDao.findById(taskId);
	}
	
	/**
	 * 
	 * @param taskNo
	 * @return
	 * @throws BaseDaoException 
	 */
	public Task findByTaskNo(String taskNo) throws BaseDaoException{
		if(null==taskNo||taskNo.equals("")){
			throw new BaseDaoException(ResponesCodeConst.QUERY_PARAMS_ERROR_CODE,"查询任务参数错误，缺少参数值");
		}
		return taskCrudDao.findByTaskNo(taskNo);
	}
	
	public int getAllTaskCountByJdbc(){
		StringBuilder sqlBuilder = new StringBuilder("select count(1) from task");
		Integer result = jdbcTemplate.queryForObject(sqlBuilder.toString(), null, Integer.class);
		return null==result?0:result;
	}

	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws BaseDaoException
	 */
	public Task getTaskByIdByJdbc(int taskId) throws BaseDaoException{
		if(taskId==0){
			throw new BaseDaoException(ResponesCodeConst.QUERY_PARAMS_ERROR_CODE,"查询任务参数错误，缺少参数值");
		}
		StringBuilder sqlBuilder = new StringBuilder("select * from task where id = ?");
		Object[] params = new Object[]{taskId};
		return jdbcTemplate.queryForObject(sqlBuilder.toString(), params, taskRowMapper);
	}
	
	/**
	 * 
	 * @param taskNo
	 * @return
	 * @throws BaseDaoException
	 */
	public Task getTaskByTaskNoByJdbc(String taskNo) throws BaseDaoException{
		if(null==taskNo||taskNo.equals("")){
			throw new BaseDaoException(ResponesCodeConst.QUERY_PARAMS_ERROR_CODE,"查询任务参数错误，缺少参数值");
		}
		StringBuilder sqlBuilder = new StringBuilder("select * from task where task_no = ?");
		Object[] params = new Object[]{taskNo};
		return jdbcTemplate.queryForObject(sqlBuilder.toString(), params, taskRowMapper);
	}
	

}
