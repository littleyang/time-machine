package com.vanke.status.machine.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.vanke.status.machine.dao.base.JdbcBaseDao;
import com.vanke.status.machine.dao.crud.TaskEventsCrudDao;
import com.vanke.status.machine.model.TaskEvents;

@Repository
@Qualifier("taskEventsDao")
public class TaskEventsDao extends JdbcBaseDao {
	
	private static RowMapper<TaskEvents> taskEventsRowMapper = new RowMapper<TaskEvents>() {
		@Override
		public TaskEvents mapRow(ResultSet rs, int index)throws SQLException {
			TaskEvents event = new TaskEvents();
			event.setId(rs.getInt("id"));
			event.setCode(rs.getString("code"));
			event.setName(rs.getString("name"));
			event.setMsg(rs.getString("msg"));
			event.setType(rs.getInt("type"));
			event.setCreated(rs.getDate("created"));
			event.setUpdated(rs.getDate("updated"));
			return event;
		}
	};
	
	@Autowired
	@Qualifier("taskEventsCrudDao")
	private TaskEventsCrudDao taskEventsCrudDao;
	
	/**
	 * 
	 * @return
	 */
	public int getAllEventsCountByCrud(){
		return taskEventsCrudDao.getAllTaskEventsCount();
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public TaskEvents getTaskByCrudDaoById(int id) {
		// TODO Auto-generated method stub
		return taskEventsCrudDao.findById(id);
	}
	
	/**
	 * 
	 * @param code
	 * @return
	 */
	public TaskEvents getTaskByCrudDaoByCode(String code) {
		// TODO Auto-generated method stub
		return taskEventsCrudDao.findByCode(code);
	}

	/**
	 * 
	 * @param taskEvent
	 * @return
	 */
	public TaskEvents createTaskEvent(TaskEvents taskEvent) {
		// TODO Auto-generated method stub
		return taskEventsCrudDao.save(taskEvent);
	}
	
	/**
	 * 删除TaskEvent
	 * @param taskEvent
	 */
	public void deleteTaskEvents(TaskEvents taskEvent){
		taskEventsCrudDao.delete(taskEvent);
	}
	
	/**
	 * 使用JDBC的方法返回所有任务的事件状态总数
	 * @return
	 */
	public int getAllEventsCountByJdbc(){
		StringBuilder sqlBuilder = new StringBuilder("select count(1) from task_events");
		return jdbcTemplate.queryForObject(sqlBuilder.toString(), null, Integer.class);
	}
	
	/**
	 * 使用JDBC方法根据某个任务事件ID返回事件的对象
	 * @param id
	 * @return
	 */
	public TaskEvents getTaskByIdByJdbc(int id){
		StringBuilder sqlBuilder = new StringBuilder("select * from task_events where id = ?");
		List<Object> params = new ArrayList<Object>();
		params.add(id);
		return jdbcTemplate.queryForObject(sqlBuilder.toString(), taskEventsRowMapper, params);
	}
	
	/**
	 * 使用JDBC方法根据某个任务事件Code返回事件的对象
	 * @param code
	 * @return
	 */
	public TaskEvents getTaskEventByCodeByJdbc(String code){
		StringBuilder sqlBuilder = new StringBuilder("select * from task_events where code = ?");
		List<Object> params = new ArrayList<Object>();
		params.add(code);
		return jdbcTemplate.queryForObject(sqlBuilder.toString(), taskEventsRowMapper, params);
	}
	
}
