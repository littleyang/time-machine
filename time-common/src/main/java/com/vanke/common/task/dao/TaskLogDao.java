package com.vanke.common.task.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.vanke.common.constant.CommonCodeConst;
import com.vanke.common.dao.base.JdbcBaseDao;
import com.vanke.common.exceptions.BaseDaoException;
import com.vanke.common.model.task.TaskLog;

@Repository
@Qualifier("taskLogDao")
public class TaskLogDao extends JdbcBaseDao{
	
	@Autowired
	@Qualifier("taskLogCrudDao")
	private TaskLogCrudDao taskLogCrudDao;
	
	/**
	 * 
	 * @param taskLog
	 * @return
	 * @throws BaseDaoException
	 */
	public TaskLog createTaskLog(TaskLog taskLog) throws BaseDaoException{
		if(null==taskLog||taskLog.getTaskNo().equals("")){
			throw new BaseDaoException(CommonCodeConst.QUERY_PARAMS_ERROR_CODE,"创建任务参数错误，缺少参数值");
		}
		return taskLogCrudDao.save(taskLog);
	}

}
