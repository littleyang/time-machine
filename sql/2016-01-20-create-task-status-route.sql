create table `status_route` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`bussiness_type` varchar (32) NOT NULL,
	`current_event` varchar (32) NOT NULL,
	`current_status` int(6) NOT NULL,
	`next_status` int(6) NOT NULL,
	`created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`updated` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;