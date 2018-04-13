package com.winterframework.efamily.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.winterframework.efamily.entity.ChinaWeather;
import com.winterframework.efamily.entity.ChinaWeatherResult;

public class WeatherUtil {

	private static final Logger log = LoggerFactory.getLogger(WeatherUtil.class);

	private String getPage(String url, String encode) {
		// 创建HttpClient实例

		// MultiThreadedHttpConnectionManager connectionManager =
		// new MultiThreadedHttpConnectionManager();
		HttpClient httpClient = new DefaultHttpClient();
		// 设置编码参数
		if (encode != null) {
			httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, encode);

		} else {
			httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "gbk");
		}
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
		httpClient.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 60000);
		// 创建GetMethod实例访问指定URL
		// GetMethod PostMethod = new GetMethod(url);
		HttpGet httpGet = new HttpGet(url);
		HttpContext httpContext = new BasicHttpContext();
		try {
			// 访问指定URL并取得返回状态码
			HttpResponse response = httpClient.execute(httpGet, httpContext);
			HttpUriRequest realRequest = (HttpUriRequest) httpContext.getAttribute(ExecutionContext.HTTP_REQUEST);

			if (realRequest.getURI().toString().contains("erro")) {
				return null;
			}

			if (response.getStatusLine().getStatusCode() == 200) {// 返回成功状态码200
				// 读取页面HTML源码
				StringBuffer sb = new StringBuffer();
				InputStream in = response.getEntity().getContent();
				BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				if (br != null)
					br.close();
				return sb.toString();
			} else {
				System.out.println(url);
				return null;
			}
		} catch (Exception ex) {
			System.out.println(ex);
			return null;
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
	}

	public static ChinaWeatherResult getWeatherData(String provinceName, String cityName) throws Exception {
		String code = WeatherInit.getCityWeatherCode(null, cityName);
		if (code == null) {
			log.error("获取城市:" + cityName + "的代码信息异常");
			return null;
		}
		String html = null;

		try {
			html = new WeatherUtil().getPage("http://m.weather.com.cn/mweather/" + code + ".shtml", "utf-8");
			if (html == null) {
				html = new WeatherUtil().getPage("http://m.weather.com.cn/mweather/" + code + ".shtml", "utf-8");
			}
		} catch (Exception e1) {
			log.error("获取城市:" + cityName + "的天气信息异常" + e1);
		}
		if (html == null) {
			log.error("无法获取http://m.weather.com.cn/mweather/" + code + ".shtml");
		} else {
			Document doc = Jsoup.parse(html);
			Document docjs = null;
			String temperatureRange = doc.select(".days7 ul li").first().select("span").text();
			temperatureRange = temperatureRange.replace("/", "~").replace("℃", "");
			ChinaWeatherResult cwr = new ChinaWeatherResult();
			cwr.setTemperature(temperatureRange);

			try {
				docjs = Jsoup.connect("http://d1.weather.com.cn/sk_2d/" + code + ".html?_=1461295083129")
						.header("Referer", "http://m.weather.com.cn/mweather/" + code + ".shtml").get();
				String jsonText = docjs.body().ownText();
				String jsonStr = jsonText.substring(jsonText.indexOf("{"));
				// System.out.println(jsonStr);
				getWeatherZhishu(code, cwr);//获取天气指数
				Gson gson = new Gson();
				ChinaWeather we = gson.fromJson(jsonStr, ChinaWeather.class);
				cwr.setWeather(we);
				return cwr;
			} catch (Exception e) {
				log.info("获取城市:" + cityName + "天气错误" + e);
			}
		}

		return null;

	}

	private static void getWeatherZhishu(String code, ChinaWeatherResult cwr) {
		Document doczhishu = null;
		try {
			doczhishu = Jsoup
					.connect("http://d1.weather.com.cn/hxpd/ski_index_7d_hxpd/" + code + ".html?_=1461822974784")
					.header("Referer", " http://e.weather.com.cn/d/mcy/" + code + ".shtml").get();
		} catch (Exception e) {
			log.error("获取天气质数url jason解析错误code:" + code);
		}

		if (doczhishu != null) {
			String jsonzhishu = doczhishu.body().ownText();
			String jsonzhishuStr = jsonzhishu.substring(jsonzhishu.indexOf("{"));

			JsonParser jsonParser = new JsonParser();
			JsonObject asJsonObject = jsonParser.parse(jsonzhishuStr).getAsJsonObject();
			Set<Entry<String, JsonElement>> entrySet = asJsonObject.entrySet();
			for (Entry<String, JsonElement> jsonentry : entrySet) {
				if (jsonentry.getValue().isJsonPrimitive()) {
					log.info(("asString--" + jsonentry.getValue().getAsString()));
				} else {
					JsonArray asJsonArray = jsonentry.getValue().getAsJsonArray();
					Set<Entry<String, JsonElement>> entrySet1 = asJsonArray.get(0).getAsJsonObject().entrySet();

					for (Entry<String, JsonElement> jsonentry1 : entrySet1) {
						if (jsonentry1.getKey().equals("002")) {
							JsonObject asJsonObject1 = jsonentry1.getValue().getAsJsonObject();
							cwr.setDress(asJsonObject1.get("002002").getAsString());
						}
						if (jsonentry1.getKey().equals("004")) {
							JsonObject asJsonObject1 = jsonentry1.getValue().getAsJsonObject();
							cwr.setGanmao(asJsonObject1.get("004002").getAsString());
						}
						if (jsonentry1.getKey().equals("007")) {
							JsonObject asJsonObject1 = jsonentry1.getValue().getAsJsonObject();
							cwr.setPollution(asJsonObject1.get("007002").getAsString());
						}
					}

				}
			}
		}
	}
}
