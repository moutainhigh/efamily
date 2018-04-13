package com.winterframework.efamily.server.protocol;
 
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.winterframework.efamily.base.utils.Base64Util;
import com.winterframework.efamily.base.utils.CharsetFactory;
import com.winterframework.efamily.base.utils.DateUtils;
import com.winterframework.efamily.base.utils.JsonUtils;
import com.winterframework.efamily.base.utils.ResourceUtils;
import com.winterframework.efamily.dto.device.param.DeviceParamCommon;
 
 
/**
 *  @author henry
 *  2012-2-4 下午01:57:33
 */
public class ProtocolUtil {
	private static Logger log = LoggerFactory.getLogger(ProtocolUtil.class); 
	private static Properties props=null;
    /**
     * 编码报文的数据部分
     * @param encode
     * @param values
     * @return
     */
    public static ByteBuf encode(int encode,Map<String,String> values,ChannelHandlerContext ctx){
    	ByteBuf totalBuffer=null;
        if (values!=null && values.size()>0) {
        	ByteBuf [] channelBuffers=new ByteBuf[values.size()];
        	try{
	            totalBuffer=ctx.alloc().buffer();
	            int index=0;
	            Charset charset=CharsetFactory.getCharset(encode);
	            for(Entry<String,String> entry:values.entrySet()){
	                String key=entry.getKey();
	                String value= entry.getValue();
	               
	                ByteBuf buffer=null;
	               /* try{*/
	                	buffer=ctx.alloc().buffer();
		                buffer.writeInt(key.getBytes(charset).length);
		                buffer.writeBytes(key.getBytes(charset));
		                
		                if(value==null||value.equals("null")){
		                	value="";
		                } 
		                if(ResourceUtils.isMediaContent(key)){
		                	byte [] contents=Base64Util.getBytesFromBASE64(value);
		                	buffer.writeInt(contents.length);
			                buffer.writeBytes(contents); 
		                }else{                
			                buffer.writeInt(value.getBytes(charset).length);
			                buffer.writeBytes(value.getBytes(charset));
		                }
		                channelBuffers[index++]=buffer;
	                /*}finally{
	                	if(buffer!=null){
	                		buffer.release();
	                	}
	                }*/
	              }
	             
	            for (int i = 0; i < channelBuffers.length; i++) {
	                totalBuffer.writeBytes(channelBuffers[i]);
	            }
            }catch(Exception e){
            	log.error("totalBuffer release.",e);
            	if(totalBuffer!=null){
            		totalBuffer.release();
            	}
        	}finally{
            	//释放内存
            	for (int i = 0; i < channelBuffers.length; i++) {
            		if(channelBuffers[i]!=null){
            			channelBuffers[i].release();
            			channelBuffers[i]=null;
            		}
	            }
            }
        }
        return totalBuffer;
    }
     
    /**
     * 解码报文的数据部分
     * @param encode
     * @param dataBuffer
     * @return
     */
    public static Map<String,String> decode(int encode,ByteBuf dataBuffer) throws Exception{
    	Map<String,String> dataMap=new HashMap<String, String>();
        if (dataBuffer!=null && dataBuffer.readableBytes()>0) {
            int processIndex=0,length=dataBuffer.readableBytes();
            Charset charset=CharsetFactory.getCharset(encode);
            int size=0;
            byte [] contents=new byte [size];
            try{
	            while(processIndex<length){
	                /**
	                 * 获取Key
	                 */
	                size=dataBuffer.readInt();
	                contents=new byte [size];
	                dataBuffer.readBytes(contents);
	                String key=new String(contents, charset);
	                processIndex+=(size+4);
	                /**
	                 * 获取Value
	                 */
	                size=dataBuffer.readInt();
	                contents=new byte [size];
	                dataBuffer.readBytes(contents); 
	                if(ResourceUtils.isMediaContent(key)){
	                	dataMap.put(key, Base64Util.getBASE64(contents));
	                }else{
	                	dataMap.put(key, new String(contents, charset));
	                } 
	                processIndex+=(size+4);
	            }
            }catch(Exception e){
        		log.error("util decode error:length="+length+" processIndex="+processIndex+" size="+size+" contents="+new String(contents,charset)+" dataMap="+JsonUtils.toJson(dataMap));
        		throw e;
        	}
        }
	    return dataMap;
    }
     
    /**
     * 获取客户端IP
     * @param channel
     * @return
     */
    public static String getClientIp(Channel channel){
        /**
         * 获取客户端IP
         */
        SocketAddress address = channel.remoteAddress();
        String ip = "";
        if (address != null) {
            ip = address.toString().trim();
            int index = ip.lastIndexOf(':');
            if (index < 1) {
                index = ip.length();
            }
            ip = ip.substring(1, index);
        }
        if (ip.length() > 15) {
            ip = ip.substring(Math.max(ip.indexOf("/") + 1, ip.length() - 15));
        }
        return ip;
    }
    /**
     * 获取客户端句柄
     * @param channel
     * @return
     */
    public static String getClientHandle(Channel channel){
    	return channel.remoteAddress().toString().split(":")[1];
    } 
    public static String getProp(String key){
    	if(null==props){
    		try {
    			props = PropertiesLoaderUtils.loadAllProperties("application.properties");
    			return (String)props.get("imageUrl");
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} 
    	}
    	return "";
    }
    
    public static void main(String[] args){ 
    	String ss="";
    	String ss2=null;
    	DeviceParamCommon s1=JsonUtils.fromJson(ss, DeviceParamCommon.class);
    			DeviceParamCommon s2=JsonUtils.fromJson(ss2, DeviceParamCommon.class);
    			 
    	 
    	Integer[] lastResult=new Integer[]{1,2,3};
		System.out.println(ArrayUtils.toString(lastResult));
    	System.out.println(DateUtils.addDays(DateUtils.currentDate(), -28000));
    }
}