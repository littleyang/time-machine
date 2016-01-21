create table `task_events` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`code` varchar (32) NOT NULL COMMENT '内部系统之间的交互编码',
	`name` varchar (32) NOT NULL COMMENT '事件的显示名称',
	`msg` varchar (64) NOT NULL COMMENT '记录事件的描述信息',
	`type` int(4) NOT NULL COMMENT '事件操作类型,100:lebang,101:zhuzher,103:第三方系统',
	`created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`updated` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`),
	UNIQUE KEY `IDX_CODE` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '任务调度对应的事件表';
