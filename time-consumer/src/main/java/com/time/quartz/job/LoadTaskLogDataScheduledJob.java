package com.time.quartz.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

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


	public void setLoadTaskLogDataFromMongoToMysqlJob(
			LoadTaskLogDataFromMongoToMysqlJob loadTaskLogDataFromMongoToMysqlJob) {
		this.loadTaskLogDataFromMongoToMysqlJob = loadTaskLogDataFromMongoToMysqlJob;
	}
 
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		loadTaskLogDataFromMongoToMysqlJob.loadDataFromMongoToMysql();
	}
  
}