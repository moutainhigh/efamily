package com.winterframework.efamily.enums;

import java.util.HashMap;
import java.util.Map;
/**
 * 功能：映射协议
 * 描述：协议号共6位，前面两位为设备类型，后面四位为操作命令。偶数为读取操作，基数为设置操作。
 * @author jason
 *
 */
public class ParameterMap {
	public static Map<Integer,String> paraIndexKeyMap=new HashMap<Integer, String>();
	static{
		paraIndexKeyMap.put(100001, "battery");                //---- （电量）:操作手表电量参数。 ----
		paraIndexKeyMap.put(100002, "service_end_time");       //---- （服务时间）：操作服务截止时间。----
		paraIndexKeyMap.put(100003, "address_list");           //---- （通讯录）：操作通讯录。 ----
		paraIndexKeyMap.put(100004, "volume");                 //---- （音量）：操作音量值。----
		paraIndexKeyMap.put(100005, "brightness");             //---- （亮度）：操作亮度值。----
		paraIndexKeyMap.put(100006, "shake");                  //---- （震动模式）：操作震动模式的值。----
		paraIndexKeyMap.put(100007, "heart_rate");             //---- （心率）：操作心率开关值。----
		paraIndexKeyMap.put(100008, "record_steps");           //---- （记步）：操作记步的开关值。----
		paraIndexKeyMap.put(100009, "firmWare_update");        //---- （固件更新）：操作固件版本。----
		paraIndexKeyMap.put(100010, "firmWare_update_file");        //---- （固件更新）：操作固件的固件文件。----
		paraIndexKeyMap.put(100011, "restore_setting");        //---- （固件更新）：恢复出厂设置。----
		paraIndexKeyMap.put(100012, "on");                 //---- （开／关机）：开机。----
		paraIndexKeyMap.put(100013, "off");                 //---- （开／关机）：关机。----
		paraIndexKeyMap.put(100014, "auto_on");            //---- （开／关机）：自动开机。----
		paraIndexKeyMap.put(100015, "auto_off");            //---- （开／关机）：自动关机。----
		paraIndexKeyMap.put(100016, "unbound");                  //---- （绑定）：解绑。----
		paraIndexKeyMap.put(100017, "quiet_mode");             //---- （静音）：操作静音模式的值。----
		paraIndexKeyMap.put(100018, "phone_number");           //---- （设备号码）：操作设备号码的值。----
		
		
		//----[[[[[[[[[[[[[[   群消息设置    ]]]]]]]]]]]]]]]]]]]]]----
		paraIndexKeyMap.put(200001, "chat_room_name");        //---- （群名）:操作群名称；----
		paraIndexKeyMap.put(200002, "chat_room_top");         //---- （置顶聊天）:操作置顶； ----
		paraIndexKeyMap.put(200003, "message_notify");        //---- （群消息通知）：操作通知；----
		paraIndexKeyMap.put(200004, "save_address_book");     //---- （保存倒通讯录）：操作保存；----
		paraIndexKeyMap.put(200005, "add_chat_room");         //---- （聊天组）：加入到聊天组----
		paraIndexKeyMap.put(200006, "quit_chat_room");        //---- （聊天组）：退出聊天组 ----
		paraIndexKeyMap.put(200007, "delete_chat_room_memeber");      //---- （聊天组）：删除聊天群组成员 ----
		
		
		
		//---------------统一的手表参数配置------------------------------
		paraIndexKeyMap.put(400001, "connect_beat"); //连网心跳频率
		paraIndexKeyMap.put(400002, "connect_timeout"); //连网超时
		paraIndexKeyMap.put(400003, "net_retry"); //连网重试次数
		paraIndexKeyMap.put(400004, "net_repeat"); //网络重启次数
		paraIndexKeyMap.put(400005, "bright_max"); //最大亮度
		paraIndexKeyMap.put(400006, "bright"); //亮度
		paraIndexKeyMap.put(400007, "sound_max"); //最大声音
		paraIndexKeyMap.put(400008, "sound"); //声音
		paraIndexKeyMap.put(400009, "signal_max"); //最大信号值
		paraIndexKeyMap.put(400010, "signal_upload_freq"); //信号上传频率
		paraIndexKeyMap.put(400011, "signal"); //信号
		paraIndexKeyMap.put(400012, "battery_max"); //最大电池值
		paraIndexKeyMap.put(400013, "battery_upload_freq"); //电池上传频率
		paraIndexKeyMap.put(400014, "location_onff"); //定位开关
		paraIndexKeyMap.put(400015, "location_gather_freq"); //定位采集频率
		paraIndexKeyMap.put(400016, "location_upload_freq"); //定位上传频率
		paraIndexKeyMap.put(400017, "health_heart_onff"); //心率开关
		paraIndexKeyMap.put(400018, "health_heart_gather_freq"); //心率采集频率
		paraIndexKeyMap.put(400019, "health_heart_upload_freq"); //心率上传频率
		paraIndexKeyMap.put(400020, "health_walk_onff"); //计步开关
		paraIndexKeyMap.put(400021, "health_walk_upload_freq"); //计步上传频率
		paraIndexKeyMap.put(400022, "vibrate_onff"); //振动模式
		paraIndexKeyMap.put(400023, "silent_onff"); //静音模式
		
		paraIndexKeyMap.put(400024, "sitting_time");            //---久坐提醒时间
		
		paraIndexKeyMap.put(400025, "sitting_time_onff");            //---久坐提醒开关
		
	}
	


}
