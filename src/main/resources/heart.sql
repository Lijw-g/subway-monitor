CREATE TABLE `heart_data` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `ip` varchar(20) NOT NULL COMMENT '设备ip地址',
  `device_id` varbinary(20) NOT NULL COMMENT '设备编号',
  `state_code` varchar(4) NOT NULL COMMENT '设备状态',
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;