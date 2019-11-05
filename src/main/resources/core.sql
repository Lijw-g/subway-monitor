CREATE TABLE `monitor_data` (
  `id` bigint(22) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `agreement` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '协议',
  `length` int(10) NOT NULL COMMENT '信息长度',
  `deviceId` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '设备id',
  `command` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '命令',
  `m_vstate` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '门控器电压',
  `m_astate` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '门控器电流',
  `m_tstate` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '门控器温度',
  `d_vstate` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '门电机电压',
  `d_astate` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '门电机电流',
  `d_tstate` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '门电机温度',
  `degree` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '开合度',
  `crc_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'crc16校验码',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=131 DEFAULT CHARSET=utf8 COMMENT='监控数据源';