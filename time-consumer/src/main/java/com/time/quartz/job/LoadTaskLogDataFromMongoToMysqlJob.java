package com.time.quartz.job;

import org.springframework.stereotype.Component;

@Component("loadTaskLogDataFromMongoToMysqlJob")
public class LoadTaskLogDataFromMongoToMysqlJob {
	
	public void loadDataFromMongoToMysql(){
		System.out.println("每天凌晨一点从mongo里面load前一天的data到mysql数据库里面............");
	}

}
