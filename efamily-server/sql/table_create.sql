-- -----------------表结构创建-------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `device_location_satellite` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `device_id` decimal(22,0) NOT NULL COMMENT '设备ID',
  `sate_number` int(11) NOT NULL COMMENT '参与定位卫星数',
  `rate` int(11) NOT NULL COMMENT '星信噪比',
  `time` decimal(15,0) NOT NULL COMMENT '时间',
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `IX_SATELLITE_DEVICE_ID` (`device_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
  
CREATE TABLE IF NOT EXISTS `device_mobile` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `device_id` decimal(22,0) NOT NULL COMMENT '设备ID',
  `mcc` varchar(10) NOT NULL COMMENT '移动用户所属国家代号',
  `mnc` varchar(10) NOT NULL COMMENT '移动网号码',
  `lac1` varchar(10) NOT NULL COMMENT '位置区码',
  `ci1` varchar(10) DEFAULT NULL COMMENT '移动基站',
  `rssi1` varchar(10) DEFAULT NULL COMMENT '信号强度',
  `lac2` varchar(10) DEFAULT NULL,
  `ci2` varchar(10) DEFAULT NULL,
  `rssi2` varchar(10) DEFAULT NULL,
  `lac3` varchar(10) DEFAULT NULL,
  `ci3` varchar(10) DEFAULT NULL,
  `rssi3` varchar(10) DEFAULT NULL,
  `lac4` varchar(10) DEFAULT NULL,
  `ci4` varchar(10) DEFAULT NULL,
  `rssi4` varchar(10) DEFAULT NULL,
  `lac5` varchar(10) DEFAULT NULL,
  `ci5` varchar(10) DEFAULT NULL,
  `rssi5` varchar(10) DEFAULT NULL,
  `time` decimal(15,0) NOT NULL COMMENT '时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `op_tag` int(11) DEFAULT '0' COMMENT '是否已被解析：0否 1是',
  PRIMARY KEY (`id`),
  KEY `IX_MOBILE_DEVICE_ID` (`device_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE IF NOT EXISTS `device_param_config` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `param_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '设备参数key 规则 分模块  eg:gps_switch 开关 gps_uprate（上传频率）',
  `param_value` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '设备参数value',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_key` (`param_key`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
  
CREATE TABLE IF NOT EXISTS `device_signal_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `device_id` decimal(22,0) NOT NULL COMMENT '设备ID',
  `level` int(11) NOT NULL COMMENT '格数',
  `time` decimal(15,0) NOT NULL COMMENT '时间',
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `IX_SIGNAL_RECORD_DEVICE_TIME` (`device_id`,`time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE IF NOT EXISTS `ef_location_gps` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` decimal(22,0) NOT NULL,
  `location` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '纬度，经度',
  `device_id` decimal(22,0) NOT NULL,
  `time` datetime NOT NULL,
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `op_tag` int(11) NOT NULL DEFAULT '0' COMMENT '地址是否已经解析 0否 1是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE IF NOT EXISTS `ef_location_wifi` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` decimal(22,0) NOT NULL COMMENT '用户ID',
  `device_id` decimal(22,0) DEFAULT NULL COMMENT '设备ID',
  `mac1` varchar(17) NOT NULL COMMENT 'MAC地址1',
  `mac2` varchar(17) NOT NULL COMMENT 'MAC地址2',
  `mac3` varchar(17) DEFAULT NULL COMMENT 'MAC地址3',
  `mac4` varchar(17) DEFAULT NULL COMMENT 'MAC地址4',
  `mac5` varchar(17) DEFAULT NULL COMMENT 'MAC地址5',
  `mac_name1` varchar(32) DEFAULT NULL COMMENT 'MAC名称1',
  `mac_name2` varchar(32) DEFAULT NULL COMMENT 'MAC名称2',
  `mac_name3` varchar(32) DEFAULT NULL COMMENT 'MAC名称3',
  `mac_name4` varchar(32) DEFAULT NULL COMMENT 'MAC名称4',
  `mac_name5` varchar(32) DEFAULT NULL COMMENT 'MAC名称5',
  `signal1` int(11) NOT NULL COMMENT '信号值1',
  `signal2` int(11) NOT NULL COMMENT '信号值2',
  `signal3` int(11) DEFAULT NULL COMMENT '信号值3',
  `signal4` int(11) DEFAULT NULL COMMENT '信号值4',
  `signal5` int(11) DEFAULT NULL COMMENT '信号值5',
  `time` decimal(15,0) NOT NULL COMMENT '采集时间',
  `op_tag` int(11) DEFAULT '0' COMMENT '是否已经被解析地址:0否 1是',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE IF NOT EXISTS `ef_lunar` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `solar_date` datetime NOT NULL COMMENT '日期',
  `lunar_date` varchar(32) NOT NULL COMMENT '农历',
  `week` int(11) NOT NULL COMMENT '星期',
  `term` varchar(8) DEFAULT NULL COMMENT '节气',
  `zodiac` varchar(10) NOT NULL COMMENT '生肖',
  `ganzhi` varchar(30) NOT NULL COMMENT '干支',
  `suit` varchar(128) NOT NULL COMMENT '宜',
  `avoid` varchar(128) NOT NULL COMMENT '忌',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `IX_LUNAR_SOLAR_DATE` (`solar_date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE IF NOT EXISTS `ef_notification_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `noty_type` decimal(22,0) NOT NULL COMMENT '消息类型(1：聊天消息 2:系统通知 3:提醒)',
  `message_id` decimal(22,0) DEFAULT NULL COMMENT '消息ID',
  `user_id` decimal(22,0) NOT NULL COMMENT '用户ID',
  `device_id` decimal(22,0) DEFAULT NULL COMMENT '设备ID',
  `status` int(3) DEFAULT '0' COMMENT '状态(0:未发送 1:已发送 2:已送达 7:已删除)',
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE IF NOT EXISTS `ef_platform_device_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `device_type` int(11) NOT NULL COMMENT '设备类型',
  `connect_freq` int(11) NOT NULL DEFAULT '0' COMMENT '连网心跳频率',
  `connect_timeout` int(11) NOT NULL DEFAULT '0' COMMENT '连网超时时间（s）',
  `connect_retry` int(11) NOT NULL DEFAULT '0' COMMENT '连网重试次数',
  `net_restart` int(11) NOT NULL DEFAULT '0' COMMENT '网络重启次数',
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE IF NOT EXISTS `ef_qrcode` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `imsi` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'SIM卡IMSI',
  `phone_number` varchar(15) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '电话号码',
  `imei` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '设备IMEI',
  `model` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '型号',
  `status` int(11) DEFAULT '0' COMMENT '状态(0:失效 1:有效)',
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `creator_id` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updator_id` int(11) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `type` int(3) DEFAULT '1' COMMENT '设备类型(1手表 2手环...)',
  `sim_status` int(3) DEFAULT '0' COMMENT '是否有卡(0无 1有)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE IF NOT EXISTS `ef_resource` (
  `id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'ID',
  `type` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '类型(image,audio.video...)',
  `ext_Type` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '扩展类型(jpg,png,mp3...)',
  `file_Path` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文件路径',
  `is_multi` int(11) NOT NULL DEFAULT '0' COMMENT '是否多清度',
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE IF NOT EXISTS `ef_resource_assist` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `resource_id` varchar(32) NOT NULL COMMENT '资源ID',
  `type` int(11) DEFAULT NULL COMMENT '类型： 1 默认头像 (headImange...)',
  `status` int(11) DEFAULT NULL COMMENT '状态：0 可用，1 禁用',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE IF NOT EXISTS `ef_weather` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `city_code` int(11) NOT NULL COMMENT '城市编码',
  `solar_date` datetime NOT NULL COMMENT '日期',
  `week` int(11) NOT NULL COMMENT '星期',
  `weather` varchar(20) NOT NULL COMMENT '天气',
  `temperature` varchar(10) NOT NULL COMMENT '温度(区间)',
  `humidity` varchar(10) DEFAULT NULL COMMENT '湿度(区间)',
  `wind` varchar(128) DEFAULT NULL COMMENT '风力指数',
  `dress` varchar(128) DEFAULT NULL COMMENT '穿衣指数',
  `ganmao` varchar(128) DEFAULT NULL COMMENT '感冒指数',
  `pollution` varchar(128) DEFAULT NULL COMMENT '污染指数',
  `renew_tag` int(11) DEFAULT '0' COMMENT '是否更新',
  `city_name` varchar(128) DEFAULT NULL,
  `temperature_current` varchar(128) DEFAULT NULL COMMENT '当前温度',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `IX_WEATHER_CITY_DATE` (`city_code`,`solar_date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE IF NOT EXISTS `ejl_battery_record` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `device_id` decimal(22,0) DEFAULT NULL COMMENT '设备ID',
  `value` int(11) NOT NULL DEFAULT '0' COMMENT '电量',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `time` decimal(15,0) DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE IF NOT EXISTS `ejl_chart_room` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '名字',
  `type` decimal(22,0) NOT NULL COMMENT '类型',
  `status` decimal(22,0) NOT NULL COMMENT '状态',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE IF NOT EXISTS `ejl_chart_room_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` decimal(22,0) NOT NULL COMMENT '用户id',
  `chart_room_id` decimal(22,0) NOT NULL COMMENT '聊天室id',
  `status` decimal(22,0) NOT NULL DEFAULT '0' COMMENT '0，还在聊天室，1 离开',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE IF NOT EXISTS `ejl_device` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '名称',
  `TYPE` int(3) NOT NULL COMMENT '1 手表；2 手环',
  `status` decimal(22,0) NOT NULL COMMENT '1  可用  （预留）',
  `code` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '设备标示符',
  `user_id` decimal(22,0) NOT NULL DEFAULT '0' COMMENT '当前设备属于哪个用户使用中',
  `family_id` decimal(22,0) NOT NULL DEFAULT '0' COMMENT '当前设备所属的家庭',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `volume` int(3) DEFAULT '0' COMMENT '声音',
  `brightness` int(3) DEFAULT '0' COMMENT '亮度',
  `quiet_mode` int(3) DEFAULT '0' COMMENT '静态模式:0开启，1关闭',
  `shake` int(3) DEFAULT '0' COMMENT '震动模式: 0开启，1关闭',
  `phone_number` varchar(20) DEFAULT '' COMMENT '设备手机号码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE IF NOT EXISTS `ejl_device_address_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '名称',
  `headimage` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '头像',
  `phonenumber` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '电话号码',
  `user_id` decimal(22,0) NOT NULL COMMENT '用户id',
  `isSos` decimal(22,0) DEFAULT NULL COMMENT '是否是SOS电话 0 :NO 1:YES',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE IF NOT EXISTS `ejl_device_monitor` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` decimal(22,0) DEFAULT NULL COMMENT '监听人',
  `device_user_id` decimal(22,0) DEFAULT NULL COMMENT '被监听人',
  `device_id` decimal(22,0) DEFAULT NULL COMMENT '被监听的设备',
  `start_time` datetime DEFAULT NULL COMMENT '监听开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '监听结束时间',
  `status` int(3) DEFAULT NULL COMMENT '0：未结束，1：已结束',
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE IF NOT EXISTS `ejl_device_parm_config` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `device_id` decimal(22,0) NOT NULL COMMENT '设备id',
  `param_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '设备参数key    规则 分模块  eg:gps_switch 开关 gps_uprate（上传频率）',
  `param_value` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '设备参数value',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_key_device_id` (`param_key`,`device_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE IF NOT EXISTS `ejl_family` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '家庭名称',
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '家庭编码（唯一编码供搜索，生成二维码）',
  `head_img` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '家庭头像',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `family_unique_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE IF NOT EXISTS `ejl_family_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` decimal(22,0) NOT NULL COMMENT '用户id',
  `family_id` decimal(22,0) NOT NULL COMMENT '家庭id',
  `status` decimal(22,0) NOT NULL DEFAULT '0' COMMENT '该用户是否还在该家庭 0 是 1否',
  `type` decimal(22,0) NOT NULL COMMENT '成员类型1 手机 2手表 3 无设备用户',
  `role_id` decimal(22,0) DEFAULT NULL COMMENT '成员角色',
  `manage_type` decimal(22,0) DEFAULT NULL COMMENT '用户加入家庭的处理类型1同意2拒绝3申请',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE IF NOT EXISTS `ejl_feedback` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `contacts` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `contactsMethod` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `content` varchar(2000) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `files` varchar(2000) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE IF NOT EXISTS `ejl_function` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '权限名',
  `type` decimal(22,0) NOT NULL COMMENT '权限类型（预留）',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE IF NOT EXISTS `ejl_health_heart_rate` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` decimal(22,0) NOT NULL COMMENT '用户id',
  `rate` decimal(22,0) NOT NULL COMMENT '心率',
  `time_span` decimal(22,0) DEFAULT NULL COMMENT '时间间隔（单位：s）',
  `device_id` decimal(22,0) NOT NULL COMMENT '设备id',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `op_tag` int(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE IF NOT EXISTS `ejl_health_sitting` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` decimal(22,0) DEFAULT NULL COMMENT '用户Id',
  `device_id` decimal(22,0) DEFAULT NULL COMMENT '设备Id',
  `start_time` datetime DEFAULT NULL COMMENT '久坐开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '久坐结束时间',
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE IF NOT EXISTS `ejl_health_sleep` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` decimal(22,0) NOT NULL,
  `device_id` decimal(22,0) NOT NULL,
  `from_time` datetime NOT NULL,
  `to_time` datetime NOT NULL,
  `deepStruc` varchar(8000) DEFAULT NULL,
  `wakeStruc` varchar(8000) DEFAULT NULL,
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE IF NOT EXISTS `ejl_health_step_count` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` decimal(22,0) NOT NULL COMMENT '用户id',
  `steps` decimal(22,0) NOT NULL COMMENT '步数',
  `begintime` datetime DEFAULT NULL COMMENT '计步起始时间',
  `endtime` datetime DEFAULT NULL COMMENT '计步结束时间',
  `device_id` decimal(22,0) NOT NULL COMMENT '设备id',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `type` int(1) DEFAULT NULL COMMENT '1:走路 2:跑步 3:登山 4:骑行 5:游泳',
  `calorie` decimal(22,0) DEFAULT NULL COMMENT '热量',
  `height` decimal(22,0) DEFAULT NULL COMMENT '登高高度',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE IF NOT EXISTS `ejl_help` (
  `id` bigint(11) NOT NULL,
  `question` varchar(2000) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `answer` varchar(2000) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `descb` varchar(2000) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `images` varchar(2000) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE IF NOT EXISTS `ejl_location` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` decimal(22,0) NOT NULL COMMENT '用户id',
  `location` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '坐标 x,y',
  `device_id` decimal(22,0) NOT NULL COMMENT '设备id',
  `type` int(11) NOT NULL COMMENT '坐标类型(1.GPS，2：高德)',
  `time` datetime NOT NULL COMMENT '位置发生时间',
  `source` int(11) NOT NULL COMMENT '来源(1:GPS 2:基站 3:WIFI 4:其它)',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE IF NOT EXISTS `ejl_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `send_user_id` decimal(22,0) NOT NULL COMMENT '消息发送方',
  `receive_user_id` decimal(22,0) NOT NULL COMMENT '消息接收方',
  `content` varchar(2000) CHARACTER SET utf8mb4 DEFAULT NULL,
  `content_type` decimal(22,0) NOT NULL COMMENT '内容类型 1 文字 2 音频 3 视频 4 图片',
  `chat_type` decimal(22,0) NOT NULL COMMENT '聊天类型 ：1 对手表发消息 2 点对点 即app 对app  3 群聊（不包含手表）',
  `chat_room_id` decimal(22,0) NOT NULL DEFAULT '0' COMMENT ' 群聊聊天室id 当聊天类型为1时 记录为手表的userid 点对点为0',
  `content_time` decimal(22,0) NOT NULL DEFAULT '0' COMMENT '消息时长',
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `ejl_message_mark` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `message_id` decimal(22,0) NOT NULL COMMENT '消息id',
  `send_user_id` decimal(22,0) NOT NULL COMMENT '消息发送方',
  `receive_user_id` decimal(22,0) NOT NULL COMMENT '消息接收方',
  `send_time` datetime NOT NULL COMMENT '发送时间',
  `send_status` decimal(22,0) NOT NULL COMMENT '发送状态 0未发送 1已发送 2 发送失败',
  `content` varchar(2000) CHARACTER SET utf8mb4 DEFAULT NULL,
  `content_type` decimal(22,0) NOT NULL COMMENT '内容类型',
  `content_time` decimal(22,0) NOT NULL DEFAULT '0' COMMENT '内容时长',
  `chat_type` decimal(22,0) unsigned NOT NULL COMMENT '聊天类型 1 对手表 2 点对点 3 群聊',
  `chat_room_id` decimal(22,0) unsigned NOT NULL DEFAULT '0' COMMENT '对手表时 为手表userid 群聊时为chatroomid 点对点时为0',
  `status` decimal(22,0) DEFAULT '0' COMMENT '状态 0 未读，1已读，2撤销，3删除',
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `ejl_p2p_chat_room` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id_litle` decimal(22,0) NOT NULL COMMENT '小id用户',
  `user_id_big` decimal(22,0) NOT NULL COMMENT '大id用户',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `ejl_public_data` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `update_flag` int(3) DEFAULT '0' COMMENT '更新标示：63,2#64,2# 包含之前版本号，以识别是否支持更新 1,一般提示;2,提示更新;3,强制更新 ',
  `device_type` int(3) DEFAULT '0' COMMENT '设备类型：1,IOS;2,ANDROID',
  `version_number` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '版本号',
  `version_describe` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '版本描述',
  `download_url` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '下载URL',
  `logo_url` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '首页URL',
  `we_chat` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '微信',
  `weibo_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '微博名称',
  `weibo_url` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '微博URL',
  `phone_number` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '电话号码',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `ejl_remind` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '提醒名称',
  `user_id` decimal(22,0) NOT NULL COMMENT '创建人',
  `content` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '提醒内容url，提醒是语音因此保存url地址',
  `send_time` datetime NOT NULL COMMENT '提醒时间',
  `send_user_id` decimal(22,0) NOT NULL COMMENT '提醒人',
  `send_method` decimal(22,0) NOT NULL COMMENT '提醒方式 1语言2视频3文本',
  `sent_status` decimal(22,0) NOT NULL COMMENT '发送提醒状态 0 未发送 1 已发送',
  `is_deleted` decimal(22,0) NOT NULL DEFAULT '0' COMMENT '删除标示 0 可用 1 已删除',
  `send_period` int(3) DEFAULT NULL COMMENT '发送周期1:不重复2:日3:工作日4:周5:月6:年',
  `send_time_end` datetime DEFAULT NULL COMMENT '结束时间',
  `send_type` decimal(22,0) NOT NULL COMMENT '类型 1 手机用户 2 手表用户',
  `remind_state` decimal(22,0) DEFAULT NULL COMMENT '0未读 1已读',
  `receive_time` datetime DEFAULT NULL COMMENT '接收时间',
  `duration_time` decimal(22,0) DEFAULT NULL COMMENT '语音视频时长',
  `device_commond` decimal(22,0) DEFAULT NULL COMMENT '1添加2修改3删除4已处理',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `ejl_remind_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `remind_id` decimal(22,0) NOT NULL COMMENT '提醒id',
  `user_id` decimal(22,0) NOT NULL COMMENT '接收人',
  `remind_state` decimal(22,0) DEFAULT NULL COMMENT '0未读 1已读',
  `is_deleted` decimal(22,0) DEFAULT NULL COMMENT '删除标示 0 可用 1 已删除',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `ejl_role` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `type` decimal(22,0) NOT NULL,
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `ejl_role_function` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `role_id` decimal(22,0) NOT NULL,
  `function_id` decimal(22,0) NOT NULL,
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `ejl_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用户名',
  `nick_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '昵称',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '电话',
  `passwd` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '密码',
  `head_img` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '头像url',
  `status` decimal(22,0) DEFAULT '0' COMMENT '状态（预留）0:在线 1:不在线',
  `family_id` decimal(22,0) DEFAULT NULL COMMENT '主家庭，该用户创建的家庭',
  `type` decimal(22,0) NOT NULL DEFAULT '0' COMMENT '类型 1 手机用户 2 手表用户 3无设备用户',
  `token` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用户token 判断登陆是否有效',
  `sex` decimal(22,0) DEFAULT NULL COMMENT '性别 0男 1女',
  `age` decimal(22,0) DEFAULT NULL COMMENT '年龄',
  `height` decimal(22,0) DEFAULT NULL COMMENT '身高 单位cm',
  `weight` decimal(22,0) DEFAULT NULL COMMENT '体重 kg',
  `signature` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `ejl_user_barrier` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` decimal(22,0) NOT NULL COMMENT '用户ID',
  `status` int(3) NOT NULL COMMENT '状态 0：关闭  1：打开 ',
  `location` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '坐标 x,y',
  `radius` decimal(22,0) NOT NULL COMMENT '围栏半径',
  `type` int(3) DEFAULT NULL COMMENT '地图类型：1 百度，2 高德',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `ejl_user_chart_room` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` decimal(22,0) NOT NULL COMMENT '用户id',
  `chat_room_id` decimal(22,0) NOT NULL COMMENT '聊天室id',
  `chat_room_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '聊天室名称',
  `chat_room_top` decimal(22,0) DEFAULT NULL COMMENT '是否放置聊天组到顶部：0 置顶，1不置顶',
  `message_notify` decimal(22,0) DEFAULT NULL COMMENT '群消息通知：0通知，1不通知',
  `save_address_book` decimal(22,0) DEFAULT NULL COMMENT '保存到通讯录：0保存，1不保存',
  `status` decimal(22,0) DEFAULT NULL COMMENT '是否还在聊天室： 0还在聊天室，1离开聊天室',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `ejl_user_device` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` decimal(22,0) NOT NULL COMMENT '用户id',
  `device_id` decimal(22,0) NOT NULL COMMENT '设备id',
  `status` decimal(22,0) NOT NULL COMMENT '状态 0 已停用（切换，换设备等） 1 使用中',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `ejl_user_extend_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` decimal(22,0) NOT NULL,
  `step_goal` decimal(22,0) DEFAULT NULL COMMENT '计步目标歩数',
  `sitTime` decimal(22,0) DEFAULT NULL COMMENT '久坐时间',
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `ejl_user_family_member` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` decimal(22,0) NOT NULL COMMENT '''用户ID''',
  `family_id` decimal(22,0) NOT NULL COMMENT '''家庭ID''',
  `member_id` decimal(22,0) NOT NULL COMMENT '成员ID EJL_USER.ID',
  `remark_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '''备注''',
  `status` decimal(22,0) DEFAULT NULL COMMENT '该用户是否为家庭成员 0 是 1 否',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `ejl_user_friend` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` decimal(22,0) NOT NULL,
  `friend_id` decimal(22,0) NOT NULL,
  `source` decimal(22,0) NOT NULL,
  `status` decimal(22,0) DEFAULT NULL,
  `remark_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `ejl_user_health_config` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` decimal(22,0) NOT NULL,
  `sitting_switch` int(1) DEFAULT NULL COMMENT '久坐开关 0关 1开',
  `step_switch` int(1) DEFAULT NULL COMMENT '记步开关 0关 1开',
  `heart_switch` int(1) DEFAULT NULL COMMENT '心率开关 0关 1开',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `device_id` decimal(22,0) DEFAULT NULL,
  `CLIMB_SWITCH` int(1) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `ejl_user_login_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` decimal(22,0) NOT NULL,
  `token` varchar(50) NOT NULL COMMENT '登录时的token',
  `login_time` datetime DEFAULT NULL COMMENT '登录时间',
  `logout_time` datetime DEFAULT NULL COMMENT '退出时间',
  `status` int(3) DEFAULT '0' COMMENT '状态：0不在线，1在线',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `token` (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `ejl_user_nickname` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `family_id` decimal(22,0) DEFAULT NULL,
  `user_id` decimal(22,0) DEFAULT NULL,
  `app_user_id` decimal(22,0) DEFAULT NULL,
  `nick_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `ejl_verify_code` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `family_id` decimal(22,0) NOT NULL COMMENT '家庭id',
  `phone_number` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '手机号',
  `time_out` decimal(22,0) NOT NULL COMMENT '失效时间（分钟）',
  `user_id` decimal(22,0) NOT NULL COMMENT '用户id',
  `type` decimal(22,0) NOT NULL COMMENT '类型 1短信验证码 2家庭邀请码',
  `verify_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '验证码',
  `status` decimal(22,0) NOT NULL COMMENT '''0无效 1有效''',
  `message_code` varchar(100) NOT NULL COMMENT '短信发送状态返回值',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE IF NOT EXISTS `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE IF NOT EXISTS `qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE IF NOT EXISTS `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE IF NOT EXISTS `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE IF NOT EXISTS `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE IF NOT EXISTS `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE IF NOT EXISTS  `statistic_user_connect` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `appcount` int(10) unsigned NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `devicecount` int(10) unsigned NOT NULL,
  `ip` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `remark` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户在线链接统计';

/*单人聊天室   */;
CREATE TABLE IF NOT EXISTS `ejl_user_p2p_chat_room` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` decimal(22,0) NOT NULL COMMENT '用户id',
  `chat_room_id` decimal(22,0) NOT NULL COMMENT '聊天室id',
  `chat_room_top` decimal(22,0) DEFAULT NULL COMMENT '是否放置聊天组到顶部：0 置顶，1不置顶',
  `message_notify` decimal(22,0) DEFAULT NULL COMMENT '群消息通知：0通知，1不通知',
  `status` decimal(22,0) DEFAULT NULL COMMENT '备用',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `ejl_attention_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` decimal(22,0) NOT NULL COMMENT '关注者用户id',
  `attention_id` decimal(22,0) NOT NULL COMMENT '被关注的用户id',
  `status` int(3) NOT NULL DEFAULT '0' COMMENT '1 申请关注 2同意关注 3 取消关注',
  `is_forbit_speak` int(3) NOT NULL DEFAULT '0' COMMENT '1 禁止发言  2 允许发言',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `apply_time` datetime DEFAULT NULL COMMENT '申请关注时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `ef_command_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `command` varchar(30) NOT NULL COMMENT '操作协议号',
  `operation` varchar(30) NOT NULL COMMENT '操作名称',
  `url` varchar(255) NOT NULL COMMENT '请求URL',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

 
CREATE TABLE IF NOT EXISTS `ef_user_weather` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `city_name` varchar(255) NOT NULL COMMENT '城市',
	`user_id` int(11) NOT NULL  COMMENT 'user id',
	`status` int(11) DEFAULT '0' COMMENT '是否已被使用：0否 1是2被覆盖',
	`weather_id` int(11) DEFAULT NULL COMMENT '记录发送的天气',
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `IX_USER_WEATHER_CITY` (`city_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*操作日志*/
CREATE TABLE IF NOT EXISTS ef_oper_log (
	id INT (11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
	user_id DECIMAL (22, 0) NOT NULL COMMENT '用户ID',
	device_id DECIMAL (22, 0) COMMENT '设备ID',
	command varchar(30) NOT NULL COMMENT '操作协议号',
	operation VARCHAR (30) NOT NULL COMMENT '操作名称',
	time datetime NOT NULL COMMENT '操作时间',
	remark VARCHAR (255) COMMENT '备注',
	creator_id DECIMAL (22, 0) NOT NULL COMMENT '创建人',
	create_time datetime NOT NULL COMMENT '创建时间',
	updator_id DECIMAL (22, 0) DEFAULT NULL,
	update_time datetime DEFAULT NULL COMMENT '更新时间',
	PRIMARY KEY (id)
) DEFAULT CHARSET=utf8;
/*定位原始坐标*/
CREATE TABLE IF NOT EXISTS ef_location_origin(
	id INT (11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  	user_id INT (11) NOT NULL COMMENT '用户ID',
  	device_id INT (11) NOT NULL COMMENT '设备ID',
	longitude DECIMAL(12,8) NOT NULL COMMENT '经度',
	latitude DECIMAL(12,8) NOT NULL COMMENT '纬度',
  	time datetime NOT NULL COMMENT '坐标时间',
	type INT(11) NOT NULL COMMENT '坐标类型',
  	source INT(11) NOT NULL COMMENT '坐标来源',
  	source_id INT (11) NOT NULL COMMENT '来源数据ID',
	city VARCHAR (30) COMMENT '城市',
  	address VARCHAR (255) COMMENT '地址',
	status INT(11) NOT NULL DEFAULT 1 COMMENT '状态(0 无效 1有效)',
	flag  INT(11) NOT NULL DEFAULT 0 COMMENT '处理标识(0未处理 9已完成)',
	remark VARCHAR (500) COMMENT '备注',
	creator_id DECIMAL (22, 0) NOT NULL COMMENT '创建人',
	create_time datetime NOT NULL COMMENT '创建时间',
	updator_id DECIMAL (22, 0) COMMENT '更新人',
	update_time datetime COMMENT '更新时间',
	PRIMARY KEY (id)
) DEFAULT CHARSET=utf8;
/*定位半成品*/
CREATE TABLE IF NOT EXISTS ef_location_semi (
	id INT (11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  	user_id INT (11) NOT NULL COMMENT '用户ID',
  	device_id INT (11) NOT NULL COMMENT '设备ID',
	longitude DECIMAL(12,8) NOT NULL COMMENT '经度',
	latitude DECIMAL(12,8) NOT NULL COMMENT '纬度',
	time datetime NOT NULL COMMENT '坐标时间',
	type INT(11) NOT NULL COMMENT '坐标类型',
	time_begin datetime NOT NULL COMMENT '开始时间',
	time_end datetime NOT NULL COMMENT '结束时间',
	previous_id  INT (11) COMMENT '前一坐标ID',
	next_id  INT (11) COMMENT '后一坐标ID',
  	source INT(11) NOT NULL COMMENT '坐标来源',
  	source_id INT (11) COMMENT '来源坐标ID',
  	status INT(11) NOT NULL DEFAULT 1 COMMENT '状态(0 无效 1有效)',
	flag  INT(11) NOT NULL DEFAULT 0 COMMENT '处理标识(0未处理 6处理坐标详情 9已完成)',
	distance DECIMAL(12,2) COMMENT '与前点距离(m)',
	city VARCHAR (30) COMMENT '城市',
	address VARCHAR (255) COMMENT '地址',
	remark VARCHAR (500) COMMENT '备注',
	creator_id DECIMAL (22, 0) NOT NULL COMMENT '创建人',
	create_time datetime NOT NULL COMMENT '创建时间',
	updator_id DECIMAL (22, 0) DEFAULT NULL,
	update_time datetime DEFAULT NULL COMMENT '更新时间',
	PRIMARY KEY (id)
) DEFAULT CHARSET=utf8;
/*定位辅助表*/
CREATE TABLE IF NOT EXISTS ef_location_assist (
	id INT (11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
	location_id INT (11) NOT NULL COMMENT '坐标ID',
	speed DECIMAL(12, 3) NOT NULL DEFAULT 0 COMMENT '速度(km/h)',
	acceleration DECIMAL(12, 2) NOT NULL DEFAULT 0 COMMENT '加速度(m/ss)',
  	direction VARCHAR (10) COMMENT '方向(NN EN EE ES SS WS WW WN)',
	slope_degree INT(11) COMMENT '斜率角度数',
	move_mode INT(11) COMMENT '移动模式',
	remark VARCHAR (500) COMMENT '备注',
	creator_id DECIMAL (22, 0) NOT NULL COMMENT '创建人',
	create_time datetime NOT NULL COMMENT '创建时间',
	updator_id DECIMAL (22, 0) COMMENT '更新人',
	update_time datetime COMMENT '更新时间',
	PRIMARY KEY (id)
) DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `ejl_third_party_login` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `token` varchar(50) DEFAULT NULL COMMENT '第三方认证的TOKEN',
  `status` int(3) NOT NULL COMMENT '状态：0 未绑定，1 绑定，2 解绑',
  `type` int(3) NOT NULL COMMENT '类型：1 腾讯QQ，2 微信，3 新浪微博',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


	CREATE TABLE IF NOT EXISTS ef_customer (
		id INT (11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
		name VARCHAR(120) NOT NULL COMMENT '客户名称',
		number int(11) NOT NULL COMMENT '客户编号:例如:100001',
		service_tel varchar(20) NOT NULL COMMENT '服务电话',
		logo_url varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '首页URL',
		we_chat varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '微信',
		weibo_name varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '微博名称',
		weibo_url varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '微博URL',
		qrcode_url varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '二维码URL',
		download_url varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'APP下载URL',
		remark varchar(255) DEFAULT NULL COMMENT '备注',
		creator_id decimal(22,0) DEFAULT NULL COMMENT '创建者',
		create_time datetime DEFAULT NULL COMMENT '创建时间',
		updator_id decimal(22,0) DEFAULT NULL COMMENT '更新者',
		update_time datetime DEFAULT NULL COMMENT '更新时间',
		PRIMARY KEY (`id`)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
-- 第三方用户key -----------------
CREATE TABLE IF NOT EXISTS ef_key(
	id INT(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  	ukey VARCHAR(32) NOT NULL COMMENT '客户key',
  	customer_id INT(11) COMMENT '客户ID',
	remark VARCHAR(500) COMMENT '备注',
	creator_id DECIMAL(22, 0) NOT NULL COMMENT '创建人',
	create_time datetime NOT NULL COMMENT '创建时间',
	updator_id DECIMAL(22, 0) COMMENT '更新人',
	update_time datetime COMMENT '更新时间',
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 第三方用户对应API列表 -------
CREATE TABLE IF NOT EXISTS ef_key_api(
  	id INT(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  	ukey VARCHAR(32) COMMENT '客户key',
  	api_ids VARCHAR(2000) COMMENT 'API的ID列表',
	remark VARCHAR(500) COMMENT '备注',
	creator_id DECIMAL(22, 0) NOT NULL COMMENT '创建人',
	create_time datetime NOT NULL COMMENT '创建时间',
	updator_id DECIMAL(22, 0) COMMENT '更新人',
	update_time datetime COMMENT '更新时间',
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- API 定义表 -------
CREATE TABLE IF NOT EXISTS `EF_API` (
`id`  int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID' ,
`number`  int(8) NOT NULL COMMENT '编号' ,
`name`  varchar(120) NOT NULL COMMENT '名称' ,
`url`  varchar(200) NOT NULL COMMENT 'url' ,
`remark`  varchar(500) COMMENT '注释' ,
`creator_id`  decimal(22,0) NOT NULL COMMENT '创建人' ,
`create_time`  datetime NOT NULL COMMENT '创建时间' ,
`updator_id`  decimal(22,0) COMMENT '更新人' ,
`update_time`  datetime COMMENT '更新时间' ,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- API 第三方 访问记录表 -------
CREATE TABLE IF NOT EXISTS `EF_API_ACCESS` (
`id`  int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID' ,
`api_id`  int(11) NOT NULL COMMENT 'APIID' ,
`ukey`  varchar(120) NOT NULL COMMENT '客户key' ,
`count`  int(8) NOT NULL DEFAULT 0 COMMENT '次数' ,
`time`  datetime NOT NULL COMMENT '访问时间' ,
`remark`  varchar(500) COMMENT '注释' ,
`creator_id`  decimal(22,0) NOT NULL COMMENT '创建人' ,
`create_time`  datetime NOT NULL COMMENT '创建时间' ,
`updator_id`  decimal(22,0) COMMENT '更新人' ,
`update_time`  datetime COMMENT '更新时间' ,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ------------2016-09-26----------------------
-- 平台 设备 设置
CREATE TABLE IF NOT EXISTS ef_platform_device_setting (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  device_type int(11) NOT NULL COMMENT '设备类型',
  connect varchar(500) DEFAULT NULL COMMENT '连网设置',
  common varchar(1000) DEFAULT NULL COMMENT '普通设置',
  frequency varchar(500) DEFAULT NULL COMMENT '频率设置',
  health varchar(1000) DEFAULT NULL COMMENT '健康设置',
  remark varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  creator_id decimal(22,0) DEFAULT NULL COMMENT '创建人',
  create_time datetime NOT NULL COMMENT '创建时间',
  updator_id decimal(22,0) DEFAULT NULL COMMENT '更新人',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 设备 设置  --偏向于 设备设置
CREATE TABLE IF NOT EXISTS ef_device_setting (
  id int(11) unsigned NOT NULL AUTO_INCREMENT,
  user_id int(11) NOT NULL COMMENT '用户ID',
  device_id int(11) NOT NULL COMMENT '设备ID',
  connect varchar(500) DEFAULT NULL COMMENT '连网设置',
  common varchar(1000) DEFAULT NULL COMMENT '普通设置',
  frequency varchar(500) DEFAULT NULL COMMENT '频率设置',
  health varchar(1000) DEFAULT NULL COMMENT '定位设置',
  remark varchar(255) DEFAULT NULL COMMENT '备注',
  creator_id decimal(22,0) DEFAULT NULL,
  create_time datetime DEFAULT NULL COMMENT '创建时间',
  updator_id decimal(22,0) DEFAULT NULL,
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 平台 健康 设置
CREATE TABLE IF NOT EXISTS ef_platform_health_setting (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  age_level int(11) NOT NULL COMMENT '年龄层级(1|50~60岁，2|60~70岁，3|70~80岁，4|80~90岁)',
  blood_high_span varchar(500) DEFAULT NULL COMMENT '血压收缩压范围（90~139）',
  blood_low_span varchar(1000) DEFAULT NULL COMMENT '血压舒张压范围（60~89）',
  rate_span varchar(500) DEFAULT NULL COMMENT '心率范围（60~100）',
  remark varchar(255) COMMENT '备注',
  creator_id decimal(22,0) DEFAULT NULL COMMENT '创建人',
  create_time datetime NOT NULL COMMENT '创建时间',
  updator_id decimal(22,0) DEFAULT NULL COMMENT '更新人',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 用户 健康 设置  --偏向于 健康数据服务
CREATE TABLE IF NOT EXISTS ef_user_health_setting (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID', 
  user_id int(11) NOT NULL COMMENT '用户ID',
  age int(11) COMMENT '年龄',
  height int(11) COMMENT '身高(cm)',
  weight decimal(5,2) COMMENT '体重(kg)',
  blood_high_span varchar(500) DEFAULT NULL COMMENT '血压收缩压范围（90~139）',
  blood_low_span varchar(1000) DEFAULT NULL COMMENT '血压舒张压范围（60~89）',
  rate_span varchar(500) DEFAULT NULL COMMENT '心率范围（60~100）',
  remark varchar(255) COMMENT '备注',
  creator_id decimal(22,0) DEFAULT NULL COMMENT '创建人',
  create_time datetime NOT NULL COMMENT '创建时间',
  updator_id decimal(22,0) DEFAULT NULL COMMENT '更新人',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 模块记录表 -------
CREATE TABLE IF NOT EXISTS `EF_MODULE` (
`id`  int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID' ,
`name`  varchar(50) NOT NULL COMMENT 'name' ,
`number`  int(11)  COMMENT '编号' ,
`status`  int(2)  COMMENT '状态:0可用 1不可用' ,
`remark`  varchar(500) COMMENT '注释' ,
`creator_id`  decimal(22,0) NOT NULL COMMENT '创建人' ,
`create_time`  datetime NOT NULL COMMENT '创建时间' ,
`updator_id`  decimal(22,0) COMMENT '更新人' ,
`update_time`  datetime COMMENT '更新时间' ,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 用户类型具备的模块表 -------
CREATE TABLE IF NOT EXISTS `EF_DEVICE_MODULE` (
`id`  int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID' ,
`customer_number`  int(11) NOT NULL COMMENT 'CUSTOMERID' ,
`glevel`  int(1) NOT NULL COMMENT '客户型号' ,
`module_id`  int(8) NOT NULL DEFAULT 0 COMMENT '功能模块' ,
`remark`  varchar(500) COMMENT '注释' ,
`creator_id`  decimal(22,0) NOT NULL COMMENT '创建人' ,
`create_time`  datetime NOT NULL COMMENT '创建时间' ,
`updator_id`  decimal(22,0) COMMENT '更新人' ,
`update_time`  datetime COMMENT '更新时间' ,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 用户消息定制表 -------
CREATE TABLE IF NOT EXISTS `EJL_USER_NOTICE` (
`id`  int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID' ,
`user_id`  int(11) NOT NULL COMMENT '设备用户' ,
`device_user_id`  int(11) NOT NULL COMMENT '发送用户' ,
`rate_status`  int(1)  COMMENT '状态1关闭 0打开' ,
`blood_status` int(1)  COMMENT '状态1关闭 0打开' ,
`diastolic_range_lt` int(4)  COMMENT '收缩压小于值' ,
`diastolic_range_gt` int(4)  COMMENT '收缩压大于值' ,
`systolic_range_lt` int(4)  COMMENT '舒张压小于值' ,
`systolic_range_gt` int(4)  COMMENT '收缩压大于值' ,
`rate_range_lt` int(4)  COMMENT '心率小于值' ,
`rate_range_gt` int(4)  COMMENT '心率大于值' ,
`remark`  varchar(500) COMMENT '注释' ,
`creator_id`  decimal(22,0) NOT NULL COMMENT '创建人' ,
`create_time`  datetime NOT NULL COMMENT '创建时间' ,
`updator_id`  decimal(22,0) COMMENT '更新人' ,
`update_time`  datetime COMMENT '更新时间' ,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- --血压表-----
CREATE TABLE IF NOT EXISTS `ejl_health_blood_pressure` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` decimal(22,0) NOT NULL COMMENT '用户id',
	`device_id` decimal(22,0) NOT NULL COMMENT '设备id',
  `rate` int(11) NOT NULL COMMENT '心率',
	`high` int(11) NOT NULL COMMENT '收缩压',
	`low` int(11) NOT NULL COMMENT '舒张压',
  `from_time`  decimal(22,0) NULL DEFAULT NULL COMMENT '开始测试时间' ,
	`to_time`  decimal(22,0) NULL DEFAULT NULL COMMENT '结束测试时间' ,
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -运动模式----
CREATE TABLE IF NOT EXISTS `EF_LOCATION_MOVEMODE` (
	`id` int(11) unsigned NOT NULL AUTO_INCREMENT,
	`user_id` decimal(22,0) NOT NULL COMMENT '用户id',
	`device_id` decimal(22,0) NOT NULL COMMENT '设备id',
	`move_mode` int(2) NOT NULL COMMENT '0静止 1步行 2跑步3骑车4汽车5火车6高铁7飞机',
	`avg_speed` decimal(12,3) NOT NULL COMMENT 'km/h',
	`min_speed` decimal(12,3) NOT NULL COMMENT 'km/h',
	`max_speed` decimal(12,3) NOT NULL COMMENT 'km/h',
	`from_time`  decimal(22,0) NULL DEFAULT NULL COMMENT '开始时间' ,
	`to_time`  decimal(22,0) NULL DEFAULT NULL COMMENT '结束时间' ,
	 cur_location varchar(32) COMMENT '当前坐标',
	`remark` varchar(255) DEFAULT NULL COMMENT '备注',
 	 `creator_id` decimal(22,0) DEFAULT NULL,
  	`create_time` datetime DEFAULT NULL COMMENT '创建时间',
 	 `updator_id` decimal(22,0) DEFAULT NULL,
 	 `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  	PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 设备操作
CREATE TABLE IF NOT EXISTS ef_device_operation (
  id int(11) unsigned NOT NULL AUTO_INCREMENT,
  user_id decimal(22,0) NOT NULL COMMENT '用户id',
  device_id decimal(22,0) NOT NULL COMMENT '设备id',
  code int(11) NOT NULL COMMENT '操作编码',
  status int(11) COMMENT '操作状态',
	time decimal(22,0)  NOT NULL COMMENT '操作时间',
  remark varchar(255) DEFAULT NULL COMMENT '备注',
  creator_id decimal(22,0) NOT NULL COMMENT '创建人',
  create_time datetime NOT NULL COMMENT '创建时间',
  updator_id decimal(22,0) DEFAULT NULL COMMENT '更新人',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- 设备告警
CREATE TABLE IF NOT EXISTS ef_device_alarm (
  id int(11) unsigned NOT NULL AUTO_INCREMENT,
  user_id int(11) NOT NULL COMMENT '用户id',
  device_id int(11) NOT NULL COMMENT '设备id',
  type int(11) NOT NULL COMMENT '告警类型(1SOS,2低信号,3低电,4进出围栏,5久坐,6睡眠,7心率,8血压)',
  value varchar(255)  COMMENT '告警信息',
  time decimal(22,0)  NOT NULL COMMENT '告警时间',
  status int(11) NOT NULL DEFAULT 0 COMMENT '-1无效0未处理 1已处理',
  remark varchar(255) DEFAULT NULL COMMENT '备注',
  creator_id decimal(22,0) NOT NULL COMMENT '创建人',
  create_time datetime NOT NULL COMMENT '创建时间',
  updator_id decimal(22,0) DEFAULT NULL COMMENT '更新人',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- 设备告警目标记录
CREATE TABLE IF NOT EXISTS ef_device_alarm_target(
  id int(11) unsigned NOT NULL AUTO_INCREMENT,
  alarm_id int(11) NOT NULL COMMENT '告警id',
  alarm_type int(11) NOT NULL COMMENT '告警类型',
  target_id int(11) NOT NULL COMMENT '告警目标id', 
  is_customer int(11) NOT NULL DEFAULT 0 COMMENT '告警目标是否客户0否1是',
  value varchar(255)  COMMENT '告警信息',
  status int(11) NOT NULL DEFAULT 0 COMMENT '0未发送 1已发送',
  remark varchar(255) DEFAULT NULL COMMENT '备注',
  creator_id decimal(22,0) NOT NULL COMMENT '创建人',
  create_time datetime NOT NULL COMMENT '创建时间',
  updator_id decimal(22,0) DEFAULT NULL COMMENT '更新人',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- 客户告警设置
CREATE TABLE IF NOT EXISTS ef_customer_alarm_setting(
  id int(11) unsigned NOT NULL AUTO_INCREMENT,
  customer_id int(11) NOT NULL COMMENT '客户id',
  alarm_type int(11) NOT NULL COMMENT '告警类型',
  alarm_url varchar(255) NOT NULL COMMENT '告警调用URL',
  status int(11) NOT NULL DEFAULT 0 COMMENT '0未发送 1已发送',
  remark varchar(255) DEFAULT NULL COMMENT '备注',
  creator_id decimal(22,0) NOT NULL COMMENT '创建人',
  create_time datetime NOT NULL COMMENT '创建时间',
  updator_id decimal(22,0) DEFAULT NULL COMMENT '更新人',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- -机构表----
CREATE TABLE IF NOT EXISTS `ef_org` (
	`id` int(11) unsigned NOT NULL AUTO_INCREMENT,
	`name`  varchar(200) NOT NULL COMMENT '机构名称',
	`number` varchar(2) NOT NULL COMMENT '编号',
	`scale` varchar(2) NOT NULL COMMENT '规模',
	`city` varchar(20) NOT NULL COMMENT '城市',
	`phone` varchar(20) NOT NULL COMMENT '联系电话',
	`ikey` varchar(32) NOT NULL COMMENT '机构key',
	`ukey` varchar(32) NOT NULL COMMENT '客户key',
	`status`  int(1)  NOT NULL COMMENT '状态1禁用 0打开' ,
	`remark` varchar(255) DEFAULT NULL COMMENT '备注',
 	`creator_id` decimal(22,0) NOT NULL,
  	`create_time` datetime NOT NULL COMMENT '创建时间',
 	`updator_id` decimal(22,0) DEFAULT NULL,
 	`update_time` datetime DEFAULT NULL COMMENT '更新时间',
  	PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- -机构设备表----
CREATE TABLE IF NOT EXISTS `ef_org_device` (
	`id` int(11) unsigned NOT NULL AUTO_INCREMENT,
	`org_id`  decimal(22,0) NOT NULL COMMENT '机构id',
	`imei` varchar(30) NOT NULL COMMENT '设备号',
	`phone` varchar(20) NOT NULL COMMENT '监护人电话',
	`status`  int(1)  COMMENT '状态1禁用 0打开' ,
	`devicePhone`  varchar(20) NOT NULL COMMENT '用户电话' ,
	`name`  varchar(20)  NOT NULL COMMENT '用户名' ,
	`sex`  decimal(22,0)  NOT NULL COMMENT '性别 0男 1女' ,
	`weight`  decimal(22,0)  NOT NULL COMMENT '体重 kg' ,
	`guardianRelation`  decimal(22,0) NOT NULL COMMENT '与监护人关系(1父子 2 母子 3父女 4母女 5其他)' ,
	`height`  decimal(22,0) NOT NULL COMMENT '身高 单位cm' ,
	`age`  decimal(22,0) NOT NULL COMMENT '年龄' ,
	`remark` varchar(255) DEFAULT NULL COMMENT '备注',
 	`creator_id` decimal(22,0) NOT NULL,
  	`create_time` datetime NOT NULL COMMENT '创建时间',
 	`updator_id` decimal(22,0) DEFAULT NULL,
 	`update_time` datetime DEFAULT NULL COMMENT '更新时间',
  	PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `org_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `org_id` decimal(22,0) NOT NULL COMMENT '机构ID',
  `user_id` decimal(22,0) DEFAULT NULL COMMENT '用户ID',
  `name` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '名字',
  `head_img` varchar(100) DEFAULT NULL COMMENT '头像url',
  `status` int(3) DEFAULT 0 COMMENT '状态 : 0 初始导入 1 绑定 2 解绑 9 删除',
  `birthday` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '生日',
  `id_number` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '身份证编码',
  `sex` int(1) DEFAULT NULL COMMENT '性别 0男 1女',
  `age` int(4) DEFAULT NULL COMMENT '年龄',
  `height` decimal(22,0) DEFAULT NULL COMMENT '身高 单位cm',
  `weight` decimal(22,0) DEFAULT NULL COMMENT '体重 kg',
  `phone_number` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '电话号码',
  `live_address` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '居住地址',
  `emergency_contact` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '紧急联系人',
  `emergency_contact_phone` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '紧急联系人电话',
  `emergency_contact_relation` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '紧急联系人关系',
  `diet_taboo` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '饮食禁忌',
  `interests` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '兴趣',
  `household_address` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '户籍地址',
  `guardian_name` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '监护人',
  `guardian_phone_number` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '监护人号码',
  `guardian_relation` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '监护人关系',
  `nations` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '民族',
  `blood_type` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '血型',
  `profession` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '职业',
  `rh_negative` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'rh阴性',
  `work_unit` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '工作单位',
  `education_degree` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '教育程度',
  `income_source` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '收入来源',
  `marital_status` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '婚姻状况',
  `health_status` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '健康状况',
  `body_status` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '身体状况',
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `IX_USER_ID` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `ef_device_alarm_last` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `device_id` int(11) NOT NULL COMMENT '设备id',
  `type` int(11) NOT NULL COMMENT '告警类型(1SOS,2低信号,3低电,4进出围栏,5久坐,6睡眠,7心率,8血压)',
  `value` varchar(255) DEFAULT NULL COMMENT '告警信息',
  `time` decimal(22,0) NOT NULL COMMENT '告警时间',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '0未处理  1已处理',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ;

CREATE TABLE IF NOT EXISTS `ef_api_access_limit` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `api_id` INT(11) NOT NULL COMMENT 'APIID',
  `ukey` VARCHAR(120) NOT NULL COMMENT '客户key',
  `min_req_limit` INT(11) NOT NULL DEFAULT '0' COMMENT '每一分钟内请求限制',
  `day_req_limit` INT(11) NOT NULL DEFAULT '0' COMMENT '一天内请求总次数限制',
  `remark` VARCHAR(500) DEFAULT NULL COMMENT '注释',
  `creator_id` DECIMAL(22,0) NOT NULL COMMENT '创建人',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `updator_id` DECIMAL(22,0) DEFAULT NULL COMMENT '更新人',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `IX_APIACCESS_APIID_UKEY_TIME` (`api_id`,`ukey`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `ef_api_limit` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `api_id` INT(11) NOT NULL COMMENT 'APIID',
  `min_req_limit` INT(11) NOT NULL DEFAULT '0' COMMENT '每一分钟内请求限制',
  `day_req_limit` INT(11) NOT NULL DEFAULT '0' COMMENT '一天内请求总次数限制',
  `remark` VARCHAR(500) DEFAULT NULL COMMENT '注释',
  `creator_id` DECIMAL(22,0) NOT NULL COMMENT '创建人',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `updator_id` DECIMAL(22,0) DEFAULT NULL COMMENT '更新人',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `IX_APIACCESS_APIID` (`api_id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `ef_key_api_limit` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `api_id` INT(11) NOT NULL COMMENT 'APIID',
  `ukey` VARCHAR(120) NOT NULL COMMENT '客户key',
  `min_req_limit` INT(11) NOT NULL DEFAULT '0' COMMENT '每一分钟内请求限制',
  `day_req_limit` INT(11) NOT NULL DEFAULT '0' COMMENT '一天内请求总次数限制',
  `remark` VARCHAR(500) DEFAULT NULL COMMENT '注释',
  `creator_id` DECIMAL(22,0) NOT NULL COMMENT '创建人',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `updator_id` DECIMAL(22,0) DEFAULT NULL COMMENT '更新人',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `IX_APIACCESS_APIID_UKEY` (`api_id`,`ukey`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `ef_key_ip` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `ukey` VARCHAR(120) NOT NULL COMMENT '客户key',
  `ip` VARCHAR(20) DEFAULT NULL COMMENT '客户访问的IP',
  `remark` VARCHAR(500) DEFAULT NULL COMMENT '注释',
  `creator_id` DECIMAL(22,0) NOT NULL COMMENT '创建人',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `updator_id` DECIMAL(22,0) DEFAULT NULL COMMENT '更新人',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `IX_APIACCESS_UKEY` (`ukey`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `ejl_user_fence` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `barrier_id` decimal(22,0) NOT NULL COMMENT '单人围栏ID',
  `fence_id` decimal(22,0) NOT NULL COMMENT '机构围栏ID',
  `user_id` decimal(22,0) NOT NULL COMMENT '用户ID',
  `status` int(3) NOT NULL COMMENT '状态 0：关闭  1：打开 ',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `org_fence` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `org_id` decimal(22,0) DEFAULT '0' COMMENT '机构ID',
  `name` varchar(50) DEFAULT '' COMMENT '围栏名称',
  `address` varchar(50) DEFAULT '' COMMENT '围栏所在地址',
  `status` int(3) NOT NULL COMMENT '状态 0：关闭  1：打开 ',
  `location` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '坐标 x,y',
  `radius` decimal(22,0) NOT NULL COMMENT '围栏半径',
  `type` int(3) DEFAULT NULL COMMENT '地图类型：1 百度，2 高德',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ;

CREATE TABLE `org_employee` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `org_id` decimal(22,0) NOT NULL COMMENT '机构ID',
  `role_id` decimal(22,0) DEFAULT NULL COMMENT '用户ID',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '名字',
  `password` varchar(32) DEFAULT NULL COMMENT '员工登录密码',
  `head_img` varchar(50) DEFAULT NULL COMMENT '头像url资源ID',
  `status` int(3) DEFAULT '0' COMMENT '状态 : 0 初始导入 1 在职 2 请假 9 离职 ',
  `login_auth` int(3) DEFAULT '0' COMMENT '状态 : 0 未开通 1 开通 ',
  `id_number` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '身份证编码',
  `birthday` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '生日',
  `sex` int(1) DEFAULT NULL COMMENT '性别 0男 1女',
  `age` int(4) DEFAULT NULL COMMENT '年龄',
  `height` decimal(22,0) DEFAULT NULL COMMENT '身高 单位cm',
  `weight` decimal(22,0) DEFAULT NULL COMMENT '体重 kg',
  `phone_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '电话号码',
  `emergency_contact` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '紧急联系人',
  `emergency_contact_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '紧急联系人电话',
  `emergency_contact_relation` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '紧急联系人关系',
  `nations` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '民族',
  `marital_status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '婚姻状况',
  `education_degree` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '教育程度',
  `political_outlook` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '政治面貌',
  `part` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '工作单位',
  `duty` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '职务',
  `contract_start_time` datetime DEFAULT NULL COMMENT '合同开始时间',
  `contract_end_time` datetime DEFAULT NULL COMMENT '合同结束时间',
  `contract_number` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '合同编号',
  `pay_range` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '薪金范围',
  `household_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '户籍地址',
  `live_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '居住地址',
  `entry_time` datetime DEFAULT NULL COMMENT '入职时间',
  `leave_time` datetime DEFAULT NULL COMMENT '离职时间',
  `off_work_start_time` datetime DEFAULT NULL COMMENT '请假开始时间',
  `off_work_end_time` datetime DEFAULT NULL COMMENT '请假结束时间',
  `create_auth_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建权限的用户',
  `create_auth_time` datetime DEFAULT NULL COMMENT '创建权限的时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `org_role` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `org_id` decimal(22,0) NOT NULL COMMENT '机构ID',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '角色名称',
  `status` int(3) DEFAULT '0' COMMENT '状态 : 0 不可用  1可用',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ;

CREATE TABLE `org_role_url` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `role_id` decimal(22,0) NOT NULL COMMENT '角色ID',
  `url_id` decimal(22,0) NOT NULL COMMENT '资源ID',
  `status` int(3) DEFAULT '0' COMMENT '状态 : 0 不可用  1可用',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `org_role_url_unique` (`role_id`,`url_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ;

CREATE TABLE `org_employee_url` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `employee_id` decimal(22,0) NOT NULL COMMENT '员工ID',
  `url_id` decimal(22,0) NOT NULL COMMENT '资源ID',
  `status` int(3) DEFAULT '0' COMMENT '状态 : 0 不可用  1可用',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `org_employee_url_unique` (`employee_id`,`url_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ;

CREATE TABLE `org_url` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `parent_id` decimal(22,0) NOT NULL COMMENT '父级ID',
  `number` int(8) NOT NULL COMMENT '编号',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '资源名称',
  `url` varchar(100) NOT NULL COMMENT 'url',
  `status` int(3) DEFAULT '0' COMMENT '状态 : 0 不可用  1可用',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `org_url_resource_unique_url` (`url`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


CREATE TABLE `org_resource` (
  `id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'ID',
  `type` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '类型(image,audio.video...)',
  `ext_Type` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '扩展类型(jpg,png,mp3...)',
  `file_Path` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文件路径',
  `is_multi` int(11) NOT NULL DEFAULT '0' COMMENT '是否多清度',
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE `org_user_healthy_file` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `org_id` decimal(22,0) NOT NULL COMMENT '机构ID',
  `org_user_id` decimal(22,0) DEFAULT NULL COMMENT '用户ID',
  `head_img` varchar(50) DEFAULT NULL COMMENT '头像url资源ID',
  `status` int(3) DEFAULT '0' COMMENT '状态 : 0 初始导入 1 在职 2 请假 9 离职 ',
  `birthday` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '生日',
  `sex` int(1) DEFAULT NULL COMMENT '性别 0男 1女',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '名字',
  `age` int(4) DEFAULT NULL COMMENT '年龄',
  `height` decimal(22,0) DEFAULT NULL COMMENT '身高 单位cm',
  `weight` decimal(22,0) DEFAULT NULL COMMENT '体重 kg',
  `blood_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '血型',
  `phone_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '电话号码',
  `id_number` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '身份证编码',
  `vision_right` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '视力右',
  `vision_left` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '视力左',
  `hearing_right` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '听力右',
  `hearing_left` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '听力左',
  `sleep_status` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '睡眠情况',
  `drug_allergy` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '药物过敏',
  `drug_use_record` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '药物使用记录',
  `exposure_history` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '暴露史',
  `operation_history` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手术史',  
  `disease` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '疾病',
  `family_disease_history` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '家庭疾病史',
  `exercise_frequency` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '锻炼频率',
  `exercise_time` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '锻炼时间',
  `exercise_content` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '锻炼内容',
  `smoke_frequency` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '吸烟频率',
  `smoke_status` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '吸烟状况',
  `smoke_amount_perDay` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '日吸烟量',
  `smoke_length` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '烟龄',  
  `drink_frequency` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '饮酒频率',
  `drink_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '饮酒类型',
  `drink_amount_perDay` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '日饮酒量',  
  `drink_length` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '酒龄', 
  `diet_frequency` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '饮食频率',
  `diet_habits` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '饮食习惯',
  `diet_time` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '饮食时间',  
  `heart_rate_diagnostic_summary` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '心率诊断总结',
  `heart_rate_intervention_program` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '心率干预计划',
  `blood_pressure_diagnostic_summary` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '血压诊断总结',
  `blood_pressure_intervention_program` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '血压干预计划',
  `blood_sugar_diagnostic_summary` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '血糖诊断总结',
  `blood_sugar_intervention_program` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '血糖干预计划',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `IX_user_id` (`org_user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `org_user_family_disease` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `org_id` decimal(22,0) NOT NULL COMMENT '机构ID',
  `org_user_id` decimal(22,0) NOT NULL COMMENT '会员ID',
  `status` int(3) DEFAULT '0' COMMENT '状态 : 0 不可用  1可用',
  `relation` varchar(20) DEFAULT NULL COMMENT '关系',
  `disease` varchar(50) DEFAULT NULL COMMENT '疾病编码',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ;



CREATE TABLE `org_employee_auth` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `org_id` decimal(22,0) NOT NULL COMMENT '机构ID',
  `org_employee_id` decimal(22,0) NOT NULL COMMENT '员工ID',
  `status` int(3) DEFAULT '0' COMMENT '状态 : 0 不可以查看  1可以查看',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `creator_id` decimal(22,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator_id` decimal(22,0) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 comment "员工可以查看那些机构的权限" ;


-- 平台 设备 版本
CREATE TABLE IF NOT EXISTS ef_platform_device_version (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  device_type int(11) NOT NULL COMMENT '设备类型',
  device_model varchar(500) DEFAULT NULL COMMENT '设备型号',
  device_version varchar(1000) DEFAULT NULL COMMENT '设备版本号',
  open_version varchar(500) DEFAULT NULL COMMENT '公开版本号',
  publish_time datetime NOT NULL COMMENT '发布时间',
  status int(11) DEFAULT 0 COMMENT '是否可用',
  remark varchar(255) DEFAULT NULL COMMENT '备注',
  creator_id decimal(22,0) DEFAULT NULL COMMENT '创建人',
  create_time datetime NOT NULL COMMENT '创建时间',
  updator_id decimal(22,0) DEFAULT NULL COMMENT '更新人',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
