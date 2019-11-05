CREATE TABLE `geographical_data` (
  `id` bigint(255) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `date` varchar(18) NOT NULL COMMENT '日期',
  `time` varchar(18) NOT NULL COMMENT '时间',
  `lat` varchar(18) NOT NULL COMMENT '经度',
  `lng` varchar(18) NOT NULL COMMENT '纬度',
  `speed` varchar(10) NOT NULL COMMENT '速度',
  `direction` varchar(10) NOT NULL COMMENT '方向',
  `sign` varchar(10) NOT NULL COMMENT '标记',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=114 DEFAULT CHARSET=utf8;