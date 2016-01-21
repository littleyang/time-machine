CREATE TABLE `business_type` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(32) NOT NULL,
  `name` varchar(32) NOT NULL,
  `parent_code` varchar(32) DEFAULT NULL,
  `url` varchar(64) DEFAULT NULL,
  `related_address_type` tinyint(4) DEFAULT '0',
  `standard_work_time_in_minute` int(11) DEFAULT NULL,
  `created_by` varchar(32) NOT NULL,
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `CODE_IDX` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=160 DEFAULT CHARSET=utf8;
