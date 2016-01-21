create table `status_route` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`bussiness_code` varchar (32) NOT NULL COMMENT '业务类型，每一种业务类型对应一系列的状态路由',
	`current_event` varchar (32) NOT NULL COMMENT '对任务的当前操作事件，触发状态变更，关联任务事件表',
	`current_status` int(6) NOT NULL COMMENT '任务的当前状态，关联任务状态表',
	`next_status` int(6) NOT NULL COMMENT '任务的下一个状态，关联任务状态表',
	`next_event` varchar (32) NOT NULL COMMENT '任务的下一个状态对应的操作事件，关联任务事件表',
	`created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`updated` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`bussiness_code`) REFERENCES `business_type` (`code`) ON DELETE CASCADE,
	FOREIGN KEY (`current_event`) REFERENCES `task_events` (`code`) ON DELETE CASCADE,
	FOREIGN KEY (`current_status`) REFERENCES `task_status` (`status`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '每一种业务类型的状态表';
