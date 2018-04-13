/**   
* @Title: FormulaUtils.java 
* @Package com.winterframework.efamily.server.utils 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2015-5-7 上午9:49:41 
* @version V1.0   
*/
package com.winterframework.efamily.utils;

import java.math.BigDecimal;

/** 
* @ClassName: FormulaUtils 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy
* @date 2015-5-7 上午9:49:41 
*  
*/
public class FormulaUtils {

	/**
	* Title: getCalorie
	* Description:计算消耗的热量公式(1) 已知体重、时间和速度 跑步热量（kcal）＝体重（kg）×运动时间（小时）×指数K 指数K＝30÷速度（分钟/400米） 
	* @param weight 千克
	* @param stepsTime 小时
	* @param speed 千米/小时
	* @throws Exception 
	* @see com.winterframework.efamily.dao.IEjlHealthStepCountDao#deleteHealthStepCount(java.lang.Long) 
	*/
	public static Double getCalorie(Double weight, Double stepsTime, Double speed) {
		double k = 60 / ((speed * 1000) / 400);
		return weight * stepsTime * 30 / k;
	}

	/**
	* Title: getCalorie
	* Description:计算消耗的热量公式(2) 已知体重、距离 跑步热量（kcal）＝体重（kg）×距离（公里）×1.036 
	* @param weight 千克
	* @param distance 千米
	* @throws Exception 
	* @see com.winterframework.efamily.dao.IEjlHealthStepCountDao#deleteHealthStepCount(java.lang.Long) 
	*/
	public static Double getCalorie(Double weight, Double distance) {
		return weight * distance * 1.036*0.1;
	}
	
	/**
	 * 耗能（焦耳）=骑车速度（m/s）*体重（kg）*9.8（重力系数）*摩擦系数*骑车时间（秒）再就是1卡路里=4200焦耳
	 * @return
	 */
	
	public static Double getRidingCalorie(Double weight,Double distance){
		return distance*weight*9.8*0.25/4200;
	}

	/**
	* Title: getMileageBySteps
	* Description:计算运动距离
	* @param height 公分
	* @param steps 步距
	* @throws Exception 
	* @see com.winterframework.efamily.dao.IEjlHealthStepCountDao#deleteHealthStepCount(java.lang.Long) 
	*/
	public static Double getMileageBySteps(Long height, Long steps) {
		BigDecimal bg = new BigDecimal(height * 0.413 * steps / 100);  
        return bg.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue(); 
	}

	/**
	* Title: getHeartRateGrade
	* Description:获取心率级别  0:健康成人的心率为60～100次/分，大多数为60～80次/分 
	   1:成人每分钟心率超过100次（一般不超过 160次/分）称为窦性心动过速。
		 常见于正常人运动、兴奋、激动、吸烟、饮酒和喝浓茶后。也可见于发热、休克、贫血、甲亢、心力衰竭及应用阿托品、肾上腺素、麻黄素等。
		2: 心率低于60次/分者（一般在40次/分以上），称为窦性心动过缓
		 。可见于长期从事重体力劳动和运动员；病理性的见于甲状腺机能低下、颅内压增高、阻塞性黄疸、以及洋地黄、奎尼丁或心得安类药物过量或中毒。
	   3:如心率低于40次/分，应考虑有房室传导阻滞,应及早进行详细检查，以便针对病因进行治疗.
	   4: 如果心率在 160～220次/分，常称为阵发性心动过速，大多见于心脏病病人，病人常有心悸、胸闷、心前区不适，应及早进行详细检查，以便针对病因进行治疗
	* @param rate 心率
	* @throws Exception 
	* @see com.winterframework.efamily.dao.IEjlHealthStepCountDao#deleteHealthStepCount(java.lang.Long) 
	*/
	public static int getHeartRateGrade(Long rate) {
		if (rate >= 60 && rate <= 100) {
			return 0;
		} else if ((rate > 100 && rate < 160)) {
			return 1;
		}else if((rate > 40 && rate < 60)){
		   return 2;
		}else if (rate <= 40){
			return 3;
		}else if(rate>=160){
			return 4;
		}
		return 0;
	}

	public static Long getStepRateGrade(Long stepGoal, Long step) {
		return (step * 100)/ stepGoal ;
	}

	public static String getVerifyCode() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 6; i++) {
			sb.append((int) (Math.random() * 10));
		}
		return sb.toString();
	}
	
	
	/// <summary>
    /// 给定的经度1，纬度1；经度2，纬度2. 计算2个经纬度之间的距离。
    /// </summary>
    /// <param name="lat1">经度1</param>
    /// <param name="lon1">纬度1</param>
    /// <param name="lat2">经度2</param>
    /// <param name="lon2">纬度2</param>
    /// <returns>距离（米）</returns>
	public static double Distance(double lat1,double lon1, double lat2,double lon2)
    {
        //用haversine公式计算球面两点间的距离。
        //经纬度转换成弧度
        lat1 = ConvertDegreesToRadians(lat1);
        lon1 = ConvertDegreesToRadians(lon1);
        lat2 = ConvertDegreesToRadians(lat2);
        lon2 = ConvertDegreesToRadians(lon2);

        //差值
        double  vLon = Math.abs(lon1 - lon2);
        double  vLat = Math.abs(lat1 - lat2);

        //h is the great circle distance in radians, great circle就是一个球体上的切面，它的圆心即是球心的一个周长最大的圆。
        double  h = HaverSin(vLat) + Math.cos(lat1) * Math.cos(lat2) * HaverSin(vLon);

        double  distance = 2 * 6371.0 * Math.asin(Math.sqrt(h));

        return distance*1000; 	
    }
	
	public static double HaverSin(double theta)
    {
        double v = Math.sin(theta / 2);
        return v * v;
    }


    /// <summary>
    /// 将角度换算为弧度。
    /// </summary>
    /// <param name="degrees">角度</param>
    /// <returns>弧度</returns>
    public static double ConvertDegreesToRadians(double degrees)
    {
        return degrees * Math.PI / 180;
    }

    public static double ConvertRadiansToDegrees(double radian)
    {
        return radian * 180.0 / Math.PI;
    }
}
