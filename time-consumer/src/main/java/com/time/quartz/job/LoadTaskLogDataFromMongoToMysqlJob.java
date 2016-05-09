package com.time.quartz.job;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.time.util.TimeDateUtil;
import com.vanke.common.exceptions.BaseDaoException;
import com.vanke.common.model.task.TaskLog;
import com.vanke.common.task.dao.TaskLogDao;
import com.vanke.status.machine.cache.TaskEventMongoCacheManager;

@Component("loadTaskLogDataFromMongoToMysqlJob")
public class LoadTaskLogDataFromMongoToMysqlJob {

	@Autowired
	private TaskEventMongoCacheManager taskEventMongoCacheManager;

	@Autowired
	private TaskLogDao taskLogDao;
	
	
	/**
	 * 每天凌晨执行导入任务，把前一天的task log导入到mysql
	 * @throws ParseException
	 * @throws BaseDaoException
	 */
	public void loadDataFromMongoToMysql() throws ParseException, BaseDaoException {
		
		Map<String, Object> dates = new HashMap<String, Object>();

		Date today = new Date();

		// yesterday 00:00:00
		Date beginDate = TimeDateUtil.getSpecifiedDayZeroHourBefore(today);

		// today 00:00:00
		Date endDate = TimeDateUtil.getSpecifiedDay24HourBefore(today);

		dates.put("gte", beginDate);
		dates.put("lte", endDate);

		List<TaskLog> logs = taskEventMongoCacheManager.getAllTaskLogBetweenDates(dates);

		System.out.println(logs.size());

//		for (int i = 0; i < logs.size(); i++) {
//			TaskLog taskLog = logs.get(i);
//			taskLogDao.createTaskLog(taskLog);
//		}
	}

}
