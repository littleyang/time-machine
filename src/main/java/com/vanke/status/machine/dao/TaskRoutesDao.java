package com.vanke.status.machine.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.collections.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.vanke.common.constant.ResponesCodeConst;
import com.vanke.common.dao.base.JdbcBaseDao;
import com.vanke.common.exceptions.BaseDaoException;
import com.vanke.status.machine.dao.crud.TaskRoutesCrudDao;
import com.vanke.status.machine.model.TaskRoutes;

@Repository
@Qualifier("taskRoutesDao")
public class TaskRoutesDao extends JdbcBaseDao {
	
	private static RowMapper<TaskRoutes> taskRouteRowMapper = new RowMapper<TaskRoutes>() {
		@Override
		public TaskRoutes mapRow(ResultSet rs, int index)throws SQLException {
			TaskRoutes route = new TaskRoutes();
			route.setId(rs.getInt("id"));
			route.setBussinessCode(rs.getString("bussiness_code"));
			route.setCurrentEvent(rs.getString("current_event"));
			route.setCurrentStatus(rs.getInt("current_status"));
			route.setNextEvent(rs.getString("next_event"));
			route.setNextStatus(rs.getInt("next_status"));
			return route;
		}
	};
	
	@Autowired
	@Qualifier("taskRoutesCrudDao")
	private TaskRoutesCrudDao taskRoutesCrudDao;
	
	/**
	 * 
	 * @param route
	 * @return
	 */
	public TaskRoutes create(TaskRoutes route){
		 return taskRoutesCrudDao.save(route);
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws BaseDaoException 
	 */
	public TaskRoutes getTaskRouteById(int id) throws BaseDaoException{
		if(id==0){
			throw new BaseDaoException(ResponesCodeConst.QUERY_PARAMS_ERROR_CODE,"查询任务调度参数错误，缺少参数值");
		}
		return taskRoutesCrudDao.findById(id);
	}
	
	/**
	 * 
	 * @param route
	 * @return
	 */
	public TaskRoutes updateTaskRoute(TaskRoutes route){
		return taskRoutesCrudDao.save(route);
	}
	
	/**
	 * 
	 * @param route
	 */
	public void deleteTaskRoute(TaskRoutes route){
		taskRoutesCrudDao.delete(route);
	}
	
	/**
	 * 
	 * @param route
	 */
	public void deleteAllTaskRoutes(){
		taskRoutesCrudDao.deleteAll();
	}
	
	/**
	 * 
	 * @param bussinessCode
	 * @return
	 * @throws BaseDaoException 
	 */
	public List<TaskRoutes> findBussinessTaskRoutes(String bussinessCode) throws BaseDaoException{
		if(bussinessCode.equals("")){
			throw new BaseDaoException(ResponesCodeConst.QUERY_PARAMS_ERROR_CODE,"查询任务调度参数错误，缺少参数值");
		}
		return taskRoutesCrudDao.findByBusinessCode(bussinessCode);
	}
	
	/**
	 * 
	 * @param bussinessCode
	 * @param currentStatus
	 * @return
	 * @throws BaseDaoException 
	 */
	public List<TaskRoutes> findNextTaskRouteEvents(String bussinessCode,int currentStatus,int operationType) throws BaseDaoException{
		if(currentStatus==0||bussinessCode.equals("")){
			throw new BaseDaoException(ResponesCodeConst.QUERY_PARAMS_ERROR_CODE,"查询任务调度参数错误，缺少参数值");
		}
		return taskRoutesCrudDao.getNextRouteEvents(bussinessCode, currentStatus,operationType);
	}
	
	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TaskRoutes> findAllByCrudDao(){
		return IteratorUtils.toList(taskRoutesCrudDao.findAll().iterator());
	}
	
	/**
	 * 
	 * @return
	 */
	public int getAllRoutesCountByJdbc(){
		StringBuilder sqlBuilder = new StringBuilder("select count(1) from task_routes");
		Integer result = jdbcTemplate.queryForObject(sqlBuilder.toString(), null, Integer.class);
		return null==result?0:result;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws BaseDaoException
	 */
	public TaskRoutes getTaskRoutesByIdByJdbc(int id) throws BaseDaoException{
		if(id==0){
			throw new BaseDaoException(ResponesCodeConst.QUERY_PARAMS_ERROR_CODE,"查询任务调度参数错误，缺少参数值");
		}
		Object[] params = new Object[]{id};
		StringBuilder sqlBuilder = new StringBuilder("select * from task_routes where id = ? ");
		return jdbcTemplate.queryForObject(sqlBuilder.toString(), params, taskRouteRowMapper);
	}
	
	/**
	 * 
	 * @param bussinessCode
	 * @param currentEvent
	 * @param currentStatus
	 * @return
	 * @throws BaseDaoException
	 */
	
	public TaskRoutes getCurrentRoutesByJdbc(String bussinessCode,String currentEvent,int currentStatus, int operationType) throws BaseDaoException{
		
		if(currentStatus==0||bussinessCode.equals("")||currentEvent.equals("")){
			throw new BaseDaoException(ResponesCodeConst.QUERY_PARAMS_ERROR_CODE,"查询任务调度参数错误，缺少参数值");
		}
		
		Object[] params = new Object[]{bussinessCode,currentEvent,currentStatus,operationType};
		StringBuilder sqlBuilder = new StringBuilder("select * from task_routes where bussiness_code = ? "
				+ "and current_event = ? and current_status = ? and type = ?");
		 return jdbcTemplate.queryForObject(sqlBuilder.toString(), params, taskRouteRowMapper);
	}
	
	/**
	 * 
	 * @param bussinessCode
	 * @param currentStatus
	 * @return
	 * @throws BaseDaoException
	 */
	public List<TaskRoutes> getNextRoutesByJdbc(String bussinessCode,int currentStatus, int operationType) throws BaseDaoException{
		if(currentStatus==0||bussinessCode.equals("")){
			throw new BaseDaoException(ResponesCodeConst.QUERY_PARAMS_ERROR_CODE,"查询任务调度参数错误，缺少参数值");
		}
		
		Object[] params = new Object[]{bussinessCode,currentStatus,operationType};
		StringBuilder sqlBuilder = new StringBuilder("select * from task_routes where bussiness_code = ? "
				+ "and current_status = ? and type = ?");
		 return jdbcTemplate.query(sqlBuilder.toString(), params, taskRouteRowMapper);
	}

	/**
	 * 
	 * @param bussinessCode
	 * @return
	 * @throws BaseDaoException
	 */
	public List<TaskRoutes> getAllBusineesRoutesByJdbc(String bussinessCode) throws BaseDaoException{
		if(bussinessCode.equals("")){
			throw new BaseDaoException(ResponesCodeConst.QUERY_PARAMS_ERROR_CODE,"查询任务调度参数错误，缺少参数值");
		}
		
		Object[] params = new Object[]{bussinessCode};
		StringBuilder sqlBuilder = new StringBuilder("select * from task_routes where bussiness_code = ?");
		
		return jdbcTemplate.query(sqlBuilder.toString(), params, taskRouteRowMapper);
	}
	
	/**
	 * 
	 * @param bussinessCode
	 * @return
	 * @throws BaseDaoException
	 */
	public List<TaskRoutes> getAllTaskRoutesByJdbc() throws BaseDaoException{
		StringBuilder sqlBuilder = new StringBuilder("select * from task_routes");
		return jdbcTemplate.query(sqlBuilder.toString(), taskRouteRowMapper);
	}

}
