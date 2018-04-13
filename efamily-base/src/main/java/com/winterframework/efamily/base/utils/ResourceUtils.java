package com.winterframework.efamily.base.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import com.winterframework.efamily.base.enums.ResourceType;
import com.winterframework.efamily.base.enums.Separator;

public class ResourceUtils {
	public static String getMediaType(String urlStr){
		return urlStr.substring(urlStr.lastIndexOf(Separator.dot)+1,urlStr.length());
	}
	public static boolean isOriginal(String urlStr){
		String extType=getMediaType(urlStr);
		return (ResourceType.ImageType.JPG.equals(extType) || ResourceType.ImageType.PNG.equals(extType)
				||ResourceType.AudioType.MP3.equals(extType));
	}
	public static boolean isUrl(String urlStr){
		return urlStr.contains("://");
	}
		
	public static boolean isMediaType(String sourceType){
		return ResourceType.IMAGE.equals(sourceType) || ResourceType.AUDIO.equals(sourceType) || ResourceType.VIDEO.equals(sourceType);
	}
	public static boolean isMediaContent(String mediaContent){
		return "mediaContent".equals(mediaContent);
	}
	
	public static String getMediaId(String mediaType){
    	return UUID.randomUUID().toString().replace("-", "");
    }
	public static String getMediaId2(String mediaType){
    	StringBuilder sb = new StringBuilder();  
    	sb.append(mediaType);
		sb.append(Separator.vertical);
    	sb.append(DateUtils.format(DateUtils.currentDate(), "yyyyMMdd"));
		sb.append(Separator.vertical);
		sb.append(UUID.randomUUID().toString().replace("-", ""));
		
		try {
			return Base64.encodeBase64String(sb.toString().getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
    }
	public static String[] parseMediaId(String mediaId){
		try {
			String info=new String(org.apache.commons.codec.binary.Base64.decodeBase64(mediaId), "UTF-8");
			return StringUtils.split(info, Separator.vertical);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public static byte[] getImage(String urlStr){
		String imageType=getMediaType(urlStr);
		try{
			URL url = new URL(urlStr);  
			URLConnection connect = url.openConnection(); 
			//打开的连接读取的输入流。 
			InputStream in = connect.getInputStream(); 
			ByteArrayOutputStream out = new ByteArrayOutputStream();    
		    try{
		    	BufferedImage bi=ImageIO.read(in);
		        ImageIO.write(bi, imageType, out);      
			}finally{
				in.close();
				out.close();
			}
		    return out.toByteArray();  
		}catch(IOException e){
			return null;
		}
        
	}
	public static byte[] getText(String urlStr){
		try{
			URL url = new URL(urlStr);  
			URLConnection connect = url.openConnection(); 
			//打开的连接读取的输入流。 
			InputStream in = connect.getInputStream(); 
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			try{
		        byte[] buffer = new byte[1024];
		        int len = -1;
		        while((len = in.read(buffer)) != -1){
		        	out.write(buffer, 0, len);
		        }
		        return out.toByteArray();
			}finally{			
				in.close();
				out.close();        
			}
		}catch(IOException e){
			return null;
		}
	}
	
	public static void toImage(byte[] bytes1){
		  
	 try {       
         ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);      
         BufferedImage bi1 =ImageIO.read(bais);      
         File w2 = new File("c://QQ.jpg");//可以是jpg,png,gif格式      
         ImageIO.write(bi1, "jpg", w2);//不管输出什么格式图片，此处不需改动      
     } catch (IOException e) {      
         e.printStackTrace();      
     }   
	}
	
	/**  
	 * 格式化byte  
	 *   
	 * @param b  
	 * @return  
	 */ 
	public static String byte2hex(byte[] b) {  
	    char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',  
	            'B', 'C', 'D', 'E', 'F' };  
	    char[] out = new char[b.length * 2];  
	    for (int i = 0; i < b.length; i++) {  
	        byte c = b[i];  
	        out[i * 2] = Digit[(c >>> 4) & 0X0F];  
	        out[i * 2 + 1] = Digit[c & 0X0F];  
	    }  
	 
	    return new String(out);  
	} 
	/**  
	 * 反格式化byte  
	 *   
	 * @param s  
	 * @return  
	 */ 
	public static byte[] hex2byte(String s) {  
	    byte[] src = s.toLowerCase().getBytes();  
	    byte[] ret = new byte[src.length / 2];  
	    for (int i = 0; i < src.length; i += 2) {  
	        byte hi = src[i];  
	        byte low = src[i + 1];  
	        hi = (byte) ((hi >= 'a' && hi <= 'f') ? 0x0a + (hi - 'a')  
	                : hi - '0');  
	        low = (byte) ((low >= 'a' && low <= 'f') ? 0x0a + (low - 'a')  
	                : low - '0');  
	        ret[i / 2] = (byte) (hi << 4 | low);  
	    }  
	    return ret;  
	}  
	public static void main(String[] a) throws InterruptedException {  
		
		
		//System.out.println(uuid);
		
		// filePath + File.separator+type + File.separator + strDate + File.separator  + fileName + FILE_SUBFIXX;
		
		for(int i=0;i<10;i++){
			 Thread.sleep(10);
			String uuid = getMediaId("");
			String fileName=System.currentTimeMillis()+"";
			String strDate = DateUtils.format(DateUtils.currentDate(),DateUtils.DATE_FORMAT_PATTERN_NO_SEPARATOR);
			//    /usr/local/nginx/html/audio/20150824/1440401837329
			System.out.println(uuid+"  image jpg  /usr/local/nginx/html/image/"+strDate+"/"+fileName +" user=999 device=100 ");
			
		}
		
		/*System.out.println(isUrl("/usr/local/nginx/html/"));
		List<Map<String,String>> weathers=new ArrayList<Map<String,String>>();
		Map<String,String> m=new HashMap<String,String>();
		m.put("1", "aaaa");
		m.put("11", "aaaa11");
		System.out.println(JsonUtils.toJson(m));
		Map<String,String> m22=new HashMap<String,String>();
		m22.put("2", "aaaa2");
		m22.put("22", "aaaa22");
		weathers.add(m);
		weathers.add(m22);
		System.out.println(JsonUtils.toJson(weathers));*/
		/*
		DeviceWeatherInitRequest r=new DeviceWeatherInitRequest();
		r.setWeather(m);
		System.out.println(JsonUtils.toJson(r));
		System.out.println(getMediaType("sssssssss"));
		String m1 = getMediaId("image");
		System.out.println(m1);
		String[] ss = parseMediaId(m1);
		System.out.println(ss[0]);
		System.out.println(ss[1]);
		System.out.println(ss[2]);
		String m2 = getMediaId("audio");
		System.out.println(m2);
		System.out.println(parseMediaId(m2)[1]);
		String m11 = getMediaId("video");
		System.out.println(m11);
		System.out.println(parseMediaId(m11)[2]);*/
	}
}
