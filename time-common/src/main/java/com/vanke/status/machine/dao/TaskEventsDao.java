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

import com.vanke.common.constant.CommonCodeConst;
import com.vanke.common.dao.base.JdbcBaseDao;
import com.vanke.common.exceptions.BaseDaoException;
import com.vanke.status.machine.dao.crud.TaskEventsCrudDao;
import com.vanke.status.machine.model.TaskEvents;
import com.vanke.status.machine.model.TaskRoutes;

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
	 * @param route
	 * @return
	 */
	public TaskEvents findNextEventByNextRoutes(TaskRoutes route,int eventType){
		return taskEventsCrudDao.findByCodeAndType(route.getCurrentEvent(),eventType);
	}
	
	/**
	 * 
	 * @param route
	 * @return
	 */
	public TaskEvents findCurrentEventByCurrentRoutes(TaskRoutes route){
		return taskEventsCrudDao.findByCode(route.getCurrentEvent());
	}
	
	/**
	 * 
	 * @param list
	 * @return
	 */
	public List<TaskEvents> findNextEventsByRoutes(List<TaskRoutes> list){
		//查找下一个操作事件
		List<TaskEvents> nextEvents = new ArrayList<TaskEvents>();

		for (int i = 0; i < list.size(); i++) {
			TaskRoutes temp = list.get(i);
			TaskEvents eventTemp = taskEventsCrudDao.findByCode(temp.getCurrentEvent());
			nextEvents.add(eventTemp);
		}
		return nextEvents;
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
	 * 
	 */
	public void deleteAllEvents() {
		// TODO Auto-generated method stub
		taskEventsCrudDao.deleteAll();
		
	}
	
	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TaskEvents> findAllByCrudDao(){
		return IteratorUtils.toList(taskEventsCrudDao.findAll().iterator());
	}
	
	/**
	 * 使用JDBC的方法返回所有任务的事件状态总数
	 * @return
	 */
	public int getAllEventsCountByJdbc(){
		StringBuilder sqlBuilder = new StringBuilder("select count(1) from task_events");
		Integer result = jdbcTemplate.queryForObject(sqlBuilder.toString(), null, Integer.class);
		return null==result?0:result;
	}
	
	/**
	 * 使用JDBC方法根据某个任务事件ID返回事件的对象
	 * @param id
	 * @return
	 * @throws BaseDaoException 
	 */
	public TaskEvents getTaskEventByIdByJdbc(int id) throws BaseDaoException{
		if(id==0){
			throw new BaseDaoException(CommonCodeConst.QUERY_PARAMS_ERROR_CODE,"查询任务调度参数错误，缺少参数值");
		}
		StringBuilder sqlBuilder = new StringBuilder("select * from task_events where id = ?");
		Object[] params = new Object[]{id};
		return jdbcTemplate.queryForObject(sqlBuilder.toString(), params, taskEventsRowMapper);
	}
	
	/**
	 * 使用JDBC方法根据某个任务事件Code返回事件的对象
	 * @param code
	 * @return
	 * @throws BaseDaoException 
	 */
	public TaskEvents getTaskEventByCodeByJdbc(String code) throws BaseDaoException{
		if(null==code||code.equals("")){
			throw new BaseDaoException(CommonCodeConst.QUERY_PARAMS_ERROR_CODE,"查询任务调度参数错误，缺少参数值");
		}
		StringBuilder sqlBuilder = new StringBuilder("select * from task_events where code = ?");
		Object[] params = new Object[]{code};
		return jdbcTemplate.queryForObject(sqlBuilder.toString(), params, taskEventsRowMapper);
	}
	
	/**
	 * 使用JDBC方法根据某个任务状态status返回状态的对象
	 * @param code
	 * @return
	 */
	public List<TaskEvents> getAllTaskStatusByJdbc(){
		StringBuilder sqlBuilder = new StringBuilder("select * from task_events");
		return jdbcTemplate.query(sqlBuilder.toString(), taskEventsRowMapper);
	}

	
}
