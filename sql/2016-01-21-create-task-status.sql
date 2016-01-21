create table `task_status` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`status` int (4) NOT NULL COMMENT '任务调度对应的状态',
	`name` varchar (32) NOT NULL COMMENT '任务调度对应的状态名称，详细状态，lebang，第三方系统使用',
	`outer_status` int (4) NOT NULL COMMENT '任务调度对应的外部状态，对外显示，主要是zhuzher',
	`outer_name` varchar (32) NOT NULL COMMENT '任务调度对应的外部状态名称，对外显示，主要是zhuzher',
	`type` int(2) NOT NULL COMMENT '任务调度类型，0：乐邦使用，1:zhuzher只用',
	`created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`updated` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`),
	UNIQUE KEY `IDX_STATUS` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '任务调度对应的状态表';
