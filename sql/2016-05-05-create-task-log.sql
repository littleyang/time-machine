create table `task_log` (
	`id` int (11) NOT NULL AUTO_INCREMENT,
	`objectId` varchar (32) COMMENT 'mongoId',
	`status` int (8) COMMENT '状态',
	`created` datetime,
	`source_id` int (8) NOT NULL COMMENT 'source',
	`rate` int (4) NOT NULL COMMENT '排序',
	`task_no` varchar (32) COMMENT 'task_no',
	`event` varchar (128) COMMENT 'event',
	`score` varchar (32) COMMENT 'score',
	`msg` varchar (512) COMMENT '描述信息',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '任务log表';