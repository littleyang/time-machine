create table `task_status` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`status` int (4) NOT NULL,
	`name` varchar (32) NOT NULL,
	`type` int(2) NOT NULL,
	`outer_status` int (4) NOT NULL,
	`created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`updated` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`),
	UNIQUE KEY `IDX_STATUS` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '任务调度对应的状态表';
