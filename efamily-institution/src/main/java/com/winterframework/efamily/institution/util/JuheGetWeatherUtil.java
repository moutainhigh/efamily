package com.winterframework.efamily.institution.util;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.entity.EfWeather;
import com.winterframework.efamily.institution.dto.JuheWeatherOperator;
import com.winterframework.efamily.institution.dto.JuheWeatherResult;
import com.winterframework.efamily.institution.dto.Pm25;
import com.winterframework.modules.spring.exetend.PropertyConfig;

public class JuheGetWeatherUtil {

	private Logger log = LoggerFactory.getLogger(JuheGetWeatherUtil.class);
	
	
	public static EfWeather getJuheWeatherDate(String cityName,String key) throws Exception {
		String url = "http://op.juhe.cn/onebox/weather/query?cityname=" + cityName+ "&key="+key;
		//String url = "http://v.juhe.cn/weather/index?cityname=" + cityName+ "&key="+key;
		
		String resultStr = BaiDuGetCityNameUtil.getResultByUrl(url);
		EfWeather weather = null;
		try{
		JuheWeatherOperator<Pm25> oper = JsonUtils.fromJson(resultStr, JuheWeatherOperator.class);
		if (oper.getErrorCode() == 0) {
			JuheWeatherResult<Pm25> juheWeatherResult =  oper.getResult();
				if(juheWeatherResult != null){
					weather = new EfWeather();
					weather.setCityName(cityName);
					try{
					weather.setCreateTime(DateUtils.convertLong2Date(juheWeatherResult.getData().getRealtime().getDataUptime()));
					}catch(Exception e){
						weather.setCreateTime(new Date());
					}
					try{
					weather.setDress(juheWeatherResult.getData().getLife().getInfo().getChuanyi()[0]);
					}catch(Exception e){
						weather.setDress(" ");
					}
					try{
					weather.setGanmao(juheWeatherResult.getData().getLife().getInfo().getGanmao()[0]);
					}catch(Exception e){
						weather.setGanmao(" ");
					}
					try{
					weather.setHumidity(juheWeatherResult.getData().getRealtime().getWeather().getHumidity());
					}catch(Exception e){
						weather.setHumidity(" ");
					}
					try{
					weather.setPollution(juheWeatherResult.getData().getLife().getInfo().getWuran()[0]);
					}catch(Exception e){
						weather.setPollution(" ");
					}
					weather.setRenewTag(1);
					try{
					weather.setSolarDate(DateUtils.parse(juheWeatherResult.getData().getRealtime().getDate()+" "+juheWeatherResult.getData().getRealtime().getTime(),DateUtils.DATETIME_FORMAT_PATTERN));
					}catch(Exception e){
						weather.setSolarDate(new Date());
					}
					
					weather.setTemperature((String)juheWeatherResult.getData().getWeather().get(0).getInfo().getNight().get(2)+"~"+(String)juheWeatherResult.getData().getWeather().get(0).getInfo().getDay().get(2));
					
					
					weather.setTemperatureCurrent(juheWeatherResult.getData().getRealtime().getWeather().getTemperature());
					
					try{
					weather.setWeek(juheWeatherResult.getData().getRealtime().getWeek());
					}catch(Exception e){
						weather.setWeek(1);
					}
					try{
					weather.setWind(juheWeatherResult.getData().getRealtime().getWind().getDirect()+juheWeatherResult.getData().getRealtime().getWind().getPower());
					}catch(Exception e){
						weather.setWind(" ");
					}
					try{
					weather.setCityCode(Integer.valueOf(juheWeatherResult.getData().getRealtime().getCity_code()));
					}catch(Exception e){
						weather.setCityCode(11);
					}
					try{
					weather.setWeather(juheWeatherResult.getData().getRealtime().getWeather().getInfo());
					}catch(Exception e){
						weather.setWeather(" ");
					}
					weather.setCreateTime(new Date());
			}
		}
		}catch(Exception e){
				JuheWeatherOperator<List<Pm25>> oper = JsonUtils.fromJson(resultStr, JuheWeatherOperator.class);
				if (oper.getErrorCode() == 0) {
					JuheWeatherResult<List<Pm25>> juheWeatherResult =  oper.getResult();
					if(juheWeatherResult != null){
						weather = new EfWeather();
						weather.setCityName(cityName);
						try{
							weather.setCreateTime(DateUtils.convertLong2Date(juheWeatherResult.getData().getRealtime().getDataUptime()));
							}catch(Exception e1){
								weather.setCreateTime(new Date());
							}
							try{
							weather.setDress(juheWeatherResult.getData().getLife().getInfo().getChuanyi()[0]);
							}catch(Exception e1){
								weather.setDress(" ");
							}
							try{
							weather.setGanmao(juheWeatherResult.getData().getLife().getInfo().getGanmao()[0]);
							}catch(Exception e1){
								weather.setGanmao(" ");
							}
							try{
							weather.setHumidity(juheWeatherResult.getData().getRealtime().getWeather().getHumidity());
							}catch(Exception e1){
								weather.setHumidity(" ");
							}
							try{
							weather.setPollution(juheWeatherResult.getData().getLife().getInfo().getWuran()[0]);
							}catch(Exception e1){
								weather.setPollution(" ");
							}
							weather.setRenewTag(1);
							try{
							weather.setSolarDate(DateUtils.parse(juheWeatherResult.getData().getRealtime().getDate()+" "+juheWeatherResult.getData().getRealtime().getTime(),DateUtils.DATETIME_FORMAT_PATTERN));
							}catch(Exception e1){
								weather.setSolarDate(new Date());
							}
							
							weather.setTemperature((String)juheWeatherResult.getData().getWeather().get(0).getInfo().getNight().get(2)+"~"+(String)juheWeatherResult.getData().getWeather().get(0).getInfo().getDay().get(2));
							
							
							weather.setTemperatureCurrent(juheWeatherResult.getData().getRealtime().getWeather().getTemperature());
							
							try{
							weather.setWeek(juheWeatherResult.getData().getRealtime().getWeek());
							}catch(Exception e1){
								weather.setWeek(1);
							}
							try{
							weather.setWind(juheWeatherResult.getData().getRealtime().getWind().getDirect()+juheWeatherResult.getData().getRealtime().getWind().getPower());
							}catch(Exception e1){
								weather.setWind(" ");
							}
							try{
							weather.setCityCode(Integer.valueOf(juheWeatherResult.getData().getRealtime().getCity_code()));
							}catch(Exception e1){
								weather.setCityCode(11);
							}
							try{
							weather.setWeather(juheWeatherResult.getData().getRealtime().getWeather().getInfo());
							}catch(Exception e1){
								weather.setWeather(" ");
							}
							weather.setCreateTime(new Date());
				}
			}
		}

		return weather;
	}
}
