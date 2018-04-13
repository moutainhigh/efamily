-- -----------------表数据 更改-------------------------------------------------------------

-- --- IOS 版本更新信息  ------
update ejl_public_data set update_flag='201604071856,3#',version_number=201604071856,version_describe='应用检测到有新版可供使用，请前往亿家联官网下载安装',download_url='http://www.ejialian365.com/software.html' where device_type=1;
-- --- ANDROID 版本更新信息  ------
update ejl_public_data set update_flag='17,2#18,3#',version_number=18 ,version_describe='v0.58（注意：此版本安装前需要先卸载当前版本应用）',download_url='http://download.ejialian365.com/app/efamily_v0.58.apk' where device_type=2;


delete from qrtz_cron_triggers  where  TRIGGER_name='lunarInitTrigger';
delete from qrtz_triggers  where  TRIGGER_name='lunarInitTrigger';
delete  from qrtz_job_details where job_name ='lunarInitTask';
delete from qrtz_cron_triggers  where  TRIGGER_name='sendMessagetrigger';
delete from qrtz_triggers  where  TRIGGER_name='sendMessagetrigger';
delete  from qrtz_job_details where job_name ='sendMessageTask';

-- --- IOS 版本更新信息  ------
update ejl_public_data set update_flag='201604071146,2#201604111643,2#201604141813,2#201604181835,2#201605051835,2#',version_number=201605051835,version_describe='应用检测到有新版可供使用，请前往亿家联官网下载安装',download_url='http://www.ejialian365.com/software.html' where device_type=1;
-- --- ANDROID 版本更新信息  ------
update ejl_public_data set update_flag='17,2#18,3#19,2#20,2#',version_number=20 ,version_describe='检测到新版本：v0.60 1.全新的围栏设置功能；2.增加多种登陆方式，使您更方便快捷；3.修复其他已知bug',download_url='http://download.ejialian365.com/app/efamily_v0.60.apk' where device_type=2;

-------------将现有用户app归属为null的设置为1 表示亿家联用户----------
update ejl_user set app_type=1 where app_type is null;

update ejl_device set bind_on_off_time=update_time where bind_on_off_time is null;

update ejl_public_data set update_flag='201604071146,2#201604111643,2#201604141813,2#201604181835,2#201605131752,2#201606231555,2#201607141501,2#',version_number=201607141501,version_describe='检测到新版本：1.修复iOS 8 无法正常登录使用的问题 2.修复一些已知bug',download_url='http://www.ejialian365.com/software.html' where device_type=1;
