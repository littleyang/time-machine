package com.vanke.common.task.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.vanke.common.constant.CommonCodeConst;
import com.vanke.common.dao.base.JdbcBaseDao;
import com.vanke.common.exceptions.BaseDaoException;
import com.vanke.common.model.task.TaskLog;

@Repository
@Qualifier("taskLogDao")
public class TaskLogDao extends JdbcBaseDao{
	
	private static RowMapper<TaskLog> tasklogRowMapper = new RowMapper<TaskLog>() {
		@Override
		public TaskLog mapRow(ResultSet rs, int index)throws SQLException {
			TaskLog taskLog = new TaskLog();
			taskLog.setId(rs.getInt("id"));
			taskLog.setTaskNo(rs.getString("task_no"));
			taskLog.setCreated(rs.getDate("created"));
			taskLog.setStatus(rs.getInt("status"));
			taskLog.setEvent(rs.getString("event"));
			taskLog.setObjectId(rs.getInt("objectId"));
			taskLog.setRate(rs.getInt("rate"));
			taskLog.setScore(rs.getString("score"));
			taskLog.setSourceId(rs.getInt("source_id"));
			taskLog.setMsg(rs.getString("msg"));
			return taskLog;
		}
	};
	
	@Autowired
	@Qualifier("taskLogCrudDao")
	private TaskLogCrudDao taskLogCrudDao;
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public TaskLog findByTaskId(int id) {
		// TODO Auto-generated method stub
		return taskLogCrudDao.findById(id);
	}
	
	/**
	 * 
	 * @param log
	 */
	public void deleteTaskLog(TaskLog log){
		taskLogCrudDao.delete(log);
	}
	
	/**
	 * 
	 */
	public void trucateAllTaskLogs(){
		taskLogCrudDao.deleteAll();
	}
	
	
	/**
	 * 
	 * @param taskLog
	 * @return
	 * @throws BaseDaoException
	 */
	public TaskLog createTaskLog(TaskLog taskLog) throws BaseDaoException{
		//if(null==taskLog||"".equals(taskLog.getTaskNo())){
		if(null==taskLog||taskLog.getTaskNo().equals("")){
			throw new BaseDaoException(CommonCodeConst.QUERY_PARAMS_ERROR_CODE,"创建任务参数错误，缺少参数值");
		}
		return taskLogCrudDao.save(taskLog);
	}
	
	
	/**
	 * 
	 * @return
	 */
	public int countAllTaskLogByJdbc(){
		StringBuilder sqlBuilder = new StringBuilder("select count(1) from task_log");
		Integer result = jdbcTemplate.queryForObject(sqlBuilder.toString(), null, Integer.class);
		return null==result?0:result;
	}
	
	/**
	 * 
	 * @param taskLogId
	 * @return
	 * @throws BaseDaoException
	 * 使用jdbc的方式根据某个id获取task log
	 */
	public TaskLog getTaskLogByJdbcById(int taskLogId) throws BaseDaoException{
		if(taskLogId==0){
			throw new BaseDaoException(CommonCodeConst.QUERY_PARAMS_ERROR_CODE,"查询任务参数错误，缺少参数值");
		}
		StringBuilder sqlBuilder = new StringBuilder("select * from task_log where id = ?");
		Object[] params = new Object[]{taskLogId};
		return jdbcTemplate.queryForObject(sqlBuilder.toString(), params, tasklogRowMapper);
	}
	
	/**
	 * 
	 * @param taskNo
	 * @return
	 * @throws BaseDaoException
	 * 使用jdbc的方式根据某个task no获取对应的所有的 task log
	 */
	public List<TaskLog> getTaskLogsByJdbcByTaskNo(String taskNo) throws BaseDaoException{
		if(null==taskNo||"".equals(taskNo)){
			throw new BaseDaoException(CommonCodeConst.QUERY_PARAMS_ERROR_CODE,"查询任务参数错误，缺少参数值");
		}
		StringBuilder sqlBuilder = new StringBuilder("select * from task_log where task_no = ?");
		Object[] params = new Object[]{taskNo};
		return jdbcTemplate.query(sqlBuilder.toString(), params, tasklogRowMapper);
	}

	

}
