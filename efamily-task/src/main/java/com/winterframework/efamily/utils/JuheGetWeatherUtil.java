package com.winterframework.efamily.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.entity.EfWeather;
import com.winterframework.efamily.entity.FutherWeather;
import com.winterframework.efamily.entity.JuheWeatherOperator;
import com.winterframework.efamily.entity.JuheWeatherResult;
import com.winterframework.efamily.entity.Pm25;
import com.winterframework.efamily.entity.Pm25Info;

public class JuheGetWeatherUtil {
	
	
	public static Map<String,Integer> weekMap = new HashMap<String,Integer>();
	static{
		weekMap.put("一", 1);
		weekMap.put("二", 2);
		weekMap.put("三", 3);
		weekMap.put("四", 4);
		weekMap.put("五", 5);
		weekMap.put("六", 6);
		weekMap.put("日", 0);
	}

	public static EfWeather getJuheWeatherDate(String cityName,String key) throws Exception {
		String url = "http://op.juhe.cn/onebox/weather/query?cityname=" + cityName+ "&key="+key;
		//String url = "http://v.juhe.cn/weather/index?cityname=" + cityName+ "&key="+key;
		
		String resultStr = BaiDuGetCityNameUtil.getResultByUrl(url);
		EfWeather weather = null;
		try{
		JuheWeatherOperator<Map> oper = JsonUtils.fromJson(resultStr, JuheWeatherOperator.class);
		if (oper.getErrorCode() == 0) {
			JuheWeatherResult<Map> juheWeatherResult =  oper.getResult();
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
					//weather.setPollution(juheWeatherResult.getData().getLife().getInfo().getWuran()[0]);
					Map p = juheWeatherResult.getData().getPm25();     
					Map pm25Info=(Map)p.get("pm25");
					weather.setPollution(pm25Info.get("quality").toString());
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
					
					try{
						handlerFutherWeather(juheWeatherResult.getData().getWeather(),weather);
					}catch(Exception e){
						weather.setWeatherList(null);
					}
			}
		}
		}catch(Exception e){
				JuheWeatherOperator<List<Map>> oper = JsonUtils.fromJson(resultStr, JuheWeatherOperator.class);
				if (oper.getErrorCode() == 0) {
					JuheWeatherResult<List<Map>> juheWeatherResult =  oper.getResult();
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
							//weather.setPollution(juheWeatherResult.getData().getLife().getInfo().getWuran()[0]);
								//weather.setPollution(juheWeatherResult.getData().getPm25().get(0).getPm25().getQuality());
								Map p = juheWeatherResult.getData().getPm25().get(0);     
								Map pm25Info=(Map)p.get("pm25");
								weather.setPollution(pm25Info.get("quality").toString());
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
							
							try{
								handlerFutherWeather(juheWeatherResult.getData().getWeather(),weather);
							}catch(Exception e1){
								weather.setWeatherList(null);
							}
				}
			}
		}

		return weather;
	}
	
	public static void handlerFutherWeather(List<FutherWeather> futherWeathers,EfWeather weather){
		List<EfWeather> list = null;
		if(futherWeathers !=null){
			list = new ArrayList<EfWeather>();
			for(FutherWeather futherWeather:futherWeathers){
				String dbDate = futherWeather.getDate();
				String insertDate = DateUtils.format(weather.getSolarDate());
				if(dbDate.equals(insertDate)){
					continue;
				}
				EfWeather efWeather = new EfWeather();
				efWeather.setCityCode(weather.getCityCode());
				efWeather.setSolarDate(DateUtils.parse(dbDate));
				efWeather.setWeek(weekMap.get(futherWeather.getWeek()));
				efWeather.setWeather(String.valueOf(futherWeather.getInfo().getDay().get(1)));
				efWeather.setWind(String.valueOf(futherWeather.getInfo().getDay().get(4)));
				efWeather.setTemperatureCurrent(String.valueOf(futherWeather.getInfo().getDay().get(2)));
				efWeather.setTemperature(String.valueOf(futherWeather.getInfo().getNight().get(2))+"~"+String.valueOf(futherWeather.getInfo().getDay().get(2)));
				efWeather.setPollution("良");
				efWeather.setCreateTime(DateUtils.currentDate());
				efWeather.setCityName(weather.getCityName());
				list.add(efWeather);
			}
			weather.setWeatherList(list);
		}
		
	}
	
}
