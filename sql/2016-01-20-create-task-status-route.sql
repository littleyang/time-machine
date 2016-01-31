create table `task_routes` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`bussiness_code` varchar (32) NOT NULL COMMENT '业务类型，每一种业务类型对应一系列的状态路由',
	`current_event` varchar (32) NOT NULL COMMENT '对任务的当前操作事件，触发状态变更，关联任务事件表',
	`current_status` int(6) NOT NULL COMMENT '任务的当前状态，关联任务状态表',
	`next_status` int(6) NOT NULL COMMENT '任务的下一个状态，关联任务状态表',
	`type` int(6) NOT NULL COMMENT '当前路由的操作类型,100:lebang,101:zhuzher,103:第三方系统',
	PRIMARY KEY (`id`),
	FOREIGN KEY (`bussiness_code`) REFERENCES `business_type` (`code`) ON DELETE CASCADE,
	FOREIGN KEY (`current_event`) REFERENCES `task_events` (`code`) ON DELETE CASCADE,
	FOREIGN KEY (`current_status`) REFERENCES `task_status` (`status`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '每一种业务类型的状态表';
