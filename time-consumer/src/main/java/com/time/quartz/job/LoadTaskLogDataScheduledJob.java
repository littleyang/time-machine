package com.time.quartz.job;

import java.text.ParseException;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.vanke.common.exceptions.BaseDaoException;

/**
 * 另外一种实现方式
 * @author yangyang
 *
 */
public class LoadTaskLogDataScheduledJob extends QuartzJobBean{
 
     
    private LoadTaskLogDataFromMongoToMysqlJob loadTaskLogDataFromMongoToMysqlJob; 

	public LoadTaskLogDataFromMongoToMysqlJob getLoadTaskLogDataFromMongoToMysqlJob() {
		return loadTaskLogDataFromMongoToMysqlJob;
	}


	public void setLoadTaskLogDataFromMongoToMysqlJob(LoadTaskLogDataFromMongoToMysqlJob loadTaskLogDataFromMongoToMysqlJob) {
		this.loadTaskLogDataFromMongoToMysqlJob = loadTaskLogDataFromMongoToMysqlJob;
	}
 
	/**
	 * 每天凌晨执行导入任务
	 */
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		
		try {
			loadTaskLogDataFromMongoToMysqlJob.loadDataFromMongoToMysql();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BaseDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
  
}