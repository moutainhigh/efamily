-- -----------------表结构修改-------------------------------------------------------------
alter table ejl_public_data add verify_code_length int(3) default 4 comment '验证码字段长度';
alter table ejl_message add column status int(11) default 0 comment '状态：0 未处理，1已处理 ';

-- ---------------------增加客服端发送消息时的时间字段--------------------------
alter table ejl_message add app_send_time decimal(22,0) not null default 0 comment "客服端发送消息的时间yyyyMMddHHmmssSSS";
alter table ejl_message add index ejl_send_user_id_app_send_time(send_user_id,app_send_time);

-- ---------------------修改用户签名字段类型，使之可以兼容表情--------------------------
alter table ejl_user modify column signature varchar(2000) CHARACTER SET utf8mb4 DEFAULT NULL ;

-- ---------------------增加当前表是谁绑定的--------------------------
alter table ejl_user_device add opt_bind_user_id decimal(22,0) not null default 0 comment "操作绑定此设备的APP用户ID"; 


-- ---------------------增加定位信息数据 --------------------------
alter table ejl_location add status int(3) DEFAULT 1 COMMENT '状态 1:可用 2:已删除';

ALTER TABLE ef_location_wifi CONVERT TO CHARACTER SET utf8;
ALTER TABLE device_mobile CONVERT TO CHARACTER SET utf8;
-- -----------------表结构修改-------------------------------------------------------------
alter table ejl_public_data add verify_code_length int(3) default 4 comment '验证码字段长度';
alter table ejl_message add column status int(11) default 0 comment '状态：0 未处理，1已处理 ';

-- ---------------------增加客服端发送消息时的时间字段--------------------------
alter table ejl_message add app_send_time decimal(22,0) not null default 0 comment "客服端发送消息的时间yyyyMMddHHmmssSSS";
alter table ejl_message add index ejl_send_user_id_app_send_time(send_user_id,app_send_time);

-- ---------------------修改用户签名字段类型，使之可以兼容表情--------------------------
alter table ejl_user modify column signature varchar(2000) CHARACTER SET utf8mb4 DEFAULT NULL ;

-- ---------------------增加当前表是谁绑定的--------------------------
alter table ejl_user_device add opt_bind_user_id decimal(22,0) not null default 0 comment "操作绑定此设备的APP用户ID"; 



alter table ejl_device add online_status int DEFAULT 1 not null COMMENT '用户是否在线 0不在线 1在线';

ALTER table ejl_device add sleeplock_status int DEFAULT 0 not null comment '睡眠监测是否已经开始 0未开始 1开始';

-- ---------------------增加定位信息数据 --------------------------
alter table ejl_location add status int(3) DEFAULT 1 COMMENT '状态 1:可用 2:已删除';
alter table ejl_location add source_id INT (11) COMMENT '来源坐标ID';
alter table ejl_location add time_stay INT (11) DEFAULT 0 COMMENT '停留时间(秒数)';
alter table ejl_location add address VARCHAR (255) COMMENT '地址';
alter table ejl_location add city VARCHAR (30) COMMENT '城市';

ALTER TABLE ef_location_wifi CONVERT TO CHARACTER SET utf8;
ALTER TABLE device_mobile CONVERT TO CHARACTER SET utf8;

alter table device_mobile add server_ip VARCHAR(15) comment '连网网关IP';
alter table device_mobile add network VARCHAR(10) comment '网络类型（GSM/GPRS/WCDMA...）';
alter table device_mobile add user_id INT (11) NOT NULL COMMENT '用户ID';

alter table ejl_family_user add position int(3) default 1 comment '职位  1家庭成员  2 家庭群主  3 家庭管理员';
alter table ejl_family_user add is_forbit_speak int(3) default 2 comment '1 禁止发言  2 允许发言';

-- ------------天气预报记录用户已经发送天气的城市-------------------
alter table ejl_user_extend_info  add column weather_city varchar(200)  COMMENT '用户已经发送天气预报的城市';

-- ------------心率增加开始结束时间--------------------------------
ALTER table ejl_health_heart_rate add column from_time decimal(22,0)  not null default 0 comment "开始测试时间";
ALTER table ejl_health_heart_rate add column to_time decimal(22,0)  not null default 0 comment "结束测试时间";



alter table ejl_user_extend_info  add column current_city varchar(200)  COMMENT '用户当前城市';


-- ------------公共数据接口修改 ------------------------------------
alter table ejl_public_data modify column update_flag varchar(200) CHARACTER SET utf8 COLLATE utf8_bin default '0' COMMENT '版本是否更新[版本号,是否更新 0 不更新,1更新]： 版本1,0#版本2,1#版本3,0';

alter table ef_location_origin add radius INT(11) NOT NULL DEFAULT 0 COMMENT '半径(米)';
alter table ef_location_semi add radius INT(11) NOT NULL DEFAULT 0 COMMENT '半径(米)';
alter table ejl_location add radius INT(11) NOT NULL DEFAULT 0 COMMENT '半径(米)';


alter table ejl_user modify column nick_name varchar(20) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '昵称';

alter table ejl_user modify column user_name varchar(20) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '用户名';

alter table ejl_device add bind_on_off_time datetime DEFAULT NULL COMMENT '设备绑定解绑时间';

-- ----------增加区分用户app归属字段----------------------------
ALTER TABLE ejl_user ADD COLUMN app_type int(3) UNSIGNED COMMENT '记录不同app但共享数据库的用户，方便以后数据分离 ，1 亿家联 2 诺安' AFTER last_login_time;
alter table ef_qrcode add customer_number int(11) not null default 0 comment "客户编号";

-- ------------2016-09-26-------------------
ALTER TABLE ejl_device ADD COLUMN software_version VARCHAR(30) COMMENT '软件版本' AFTER family_id;

-- ---------------------增加手表运行模式数据[确认默认情况下是开机] --------------------------
alter table ejl_device add running_mode int(3) DEFAULT 1 COMMENT '运行模式：  1:开机     2:关机   3：开机充电中  4：关机充电中';

-- -添加设备型号------------
alter table ef_qrcode add glevel int(3) not null default 0 comment "设备型号" AFTER status;
-- -添加版本号------------
ALTER TABLE ef_qrcode ADD COLUMN software_version VARCHAR(30) COMMENT '软件版本' AFTER glevel;

-- 添加健康字段
alter table ejl_user_extend_info  add column sitting_span VARCHAR (500) COMMENT '久坐时间段'  AFTER remark;
alter table ejl_user_extend_info  add column sleep_span VARCHAR (500) COMMENT '睡眠时间段' AFTER remark;


alter table ejl_user_health_config  add column sleep_switch int(1) not null DEFAULT 0 COMMENT '睡眠开关0关1开' AFTER remark;


-- 添加qrcode_resource_id字段
alter table ejl_device  add column qrcode_resource_id varchar(32) DEFAULT NULL COMMENT '对应二维码图片的sourceId'  AFTER remark;
alter table ef_qrcode   add column qrcode_resource_id varchar(32) DEFAULT NULL COMMENT '对应二维码图片的sourceId' AFTER remark;

ALTER TABLE ef_location_semi ADD COLUMN susp_score decimal(4,1) NOT NULL DEFAULT 0 COMMENT '可疑分值' AFTER flag;
ALTER TABLE ef_location_semi ADD COLUMN aggr_count int(11) NOT NULL DEFAULT 0 COMMENT '聚合点数' AFTER susp_score;

ALTER TABLE ejl_user_barrier add column orgTag int(1) not null DEFAULT 0 comment '是否为机构设置 0否 1是' AFTER remark;
 
alter  table ef_customer add timezone VARCHAR(10) not null DEFAULT 'GMT+08' COMMENT '时区' AFTER download_url;

-- --------2016-10-18 -----------
alter table ejl_device  add column software_status int(11) DEFAULT 0 COMMENT '软件版本更新状态'  AFTER software_version;
alter table ejl_device  add column software_updator_id int(11) DEFAULT NULL COMMENT '软件版本更新人'  AFTER software_status;
alter table ejl_device  add column software_update_time datetime DEFAULT NULL COMMENT '软件版本更新时间'  AFTER software_updator_id;
alter table ejl_device  add column software_finish_time datetime DEFAULT NULL COMMENT '软件版本更新完成时间'  AFTER software_update_time;
alter table ejl_device  add column oper_type int(11) COMMENT '操作类型'  AFTER software_finish_time;
alter table ejl_device  add column oper_status int(11) DEFAULT 0 COMMENT '操作状态0初始1请求2开始3结束'  AFTER oper_type;
alter table ejl_device  add column oper_user_id int(11) COMMENT '操作用户ID'  AFTER oper_status;
alter table ejl_device  add column oper_time datetime COMMENT '操作时间'  AFTER oper_user_id;
alter table ejl_device  add column oper_finish_time datetime COMMENT '操作结束时间'  AFTER oper_time;
alter table ejl_device  add column bind_user_id int(11) COMMENT '绑定用户ID'  AFTER phone_number;
alter table ejl_device  add column bind_time datetime COMMENT '绑定时间'  AFTER bind_user_id;
alter table ejl_device  add column bind_finish_time datetime COMMENT '绑定完成时间'  AFTER bind_time;
alter table ef_user_health_setting add arm int(11) COMMENT '臂长' AFTER weight;
---- 监护人电话修改为可以为空
alter table ef_org_device modify column `phone` varchar(20) DEFAULT NULL COMMENT '监护人电话' ;

---- PC端增加此三个字段  确认是否对康朵是否有影响
ALTER TABLE ef_org ADD COLUMN customer_id int(11) DEFAULT NULL  COMMENT '客户ID' AFTER number;
ALTER TABLE ef_org ADD COLUMN province VARCHAR(30) DEFAULT NULL COMMENT '省份' AFTER scale;
ALTER TABLE ef_org ADD COLUMN county VARCHAR(30) DEFAULT NULL COMMENT '区县' AFTER city; 




--------定位数据添加结束时间------------------
alter table ejl_location ADD COLUMN time_end datetime DEFAULT NULL COMMENT '位置发生最后时间';


------告警添加第三方接口调用方法--------------

alter table ef_customer_alarm_setting add column method varchar(10) DEFAULT NULL COMMENT '请求方式'  AFTER status;

alter table ef_platform_device_version add customer_number int(11) not null comment "客户编码"  after device_model;
