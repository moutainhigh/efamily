package com.winterframework.efamily.server.protocol;
 
import java.util.HashMap;
import java.util.Map;

import com.winterframework.efamily.base.enums.YesNo;
import com.winterframework.efamily.base.utils.CharsetFactory;
 
/**
 *  @author hankchen
 *  2012-2-3 下午02:46:41
 */
 
/**
 * 请求数据
 */
 
/**
 * 通用协议介绍
 * 
 * 通用报文格式：无论是请求还是响应，报文都由一个通用报文头和实际数据组成。报文头在前，数据在后
 * （1）报文头:由数据解析类型，数据解析方法，编码，扩展字节，包长度组成,共16个字节：
 * 编码方式（1byte）、加密（1byte）、扩展1（1byte）、扩展2（1byte）、会话ID（4byte）、命令或者结果码（4byte）、包长（4byte）
 * （2）数据:由包长指定。请求或回复数据。类型对应为JAVA的Map<String,String>
 * 数据格式定义：
 * 字段1键名长度    字段1键名 字段1值长度    字段1值
 * 字段2键名长度    字段2键名 字段2值长度    字段2值
 * 字段3键名长度    字段3键名 字段3值长度    字段3值
 * …    …    …    …
 * 长度为整型，占4个字节
 */
public class FmlRequest{
    private byte encode;// 数据编码格式。已定义：0：UTF-8，1：GBK，2：GB2312，3：ISO8859-1
    private byte encrypt;// 加密类型。0表示不加密
    private byte version;// 协议版本号
    private byte clinetType;
    private byte extend;// 用于扩展协议。暂未定义任何值 
    private long sessionId;// 会话ID
    private int status=-1; 
    private int command;// 命令
    private String token;//32byte
    private int length;// 数据包长
    
     
    private Map<String,String> data=new HashMap<String, String>(); //参数
     
    private String ip;
 
    public FmlRequest(){
    	this.encode=(byte)CharsetFactory.CharsetEnum.UTF8.getCode();
    	this.encrypt=(byte)YesNo.NO.getValue();
    	this.version=1;
    	this.extend=0; 
    }
    
    public byte getEncode() {
        return encode;
    }
 
    public void setEncode(byte encode) {
        this.encode = encode;
    }
 
    public byte getEncrypt() {
        return encrypt;
    }
 
    public void setEncrypt(byte encrypt) {
        this.encrypt = encrypt;
    }
 
    public byte getVersion() {
		return version;
	}

	public void setVersion(byte version) {
		this.version = version;
	}

	public byte getExtend() {
		return extend;
	}

	public void setExtend(byte extend) {
		this.extend = extend;
	} 
	public long getSessionId() {
        return sessionId;
    }
 
    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    } 
	public int getCommand() {
        return command;
    }
 
    public void setCommand(int command) {
        this.command = command;
    }
 
    public int getLength() {
        return length;
    }
 
    public void setLength(int length) {
        this.length = length;
    }
 
    public Map<String, String> getData() {
        return data;
    }
     
    public void setValue(String key,String value){
        data.put(key, value);
    }
     
    public String getValue(String key){
        if (key==null) {
            return null;
        }
        return data.get(key);
    }
 
    public String getIp() {
        return ip;
    }
 
    public void setIp(String ip) {
        this.ip = ip;
    }
 
    public void setData(Map<String, String> data) {
        this.data = data;
    }
 
    public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	public byte getClinetType() {
		return clinetType;
	}

	public void setClinetType(byte clinetType) {
		this.clinetType = clinetType;
	}

	@Override
    public String toString() { 		    
        return "FmlRequest [encode=" + encode + ", encrypt=" + encrypt + ", version=" + version  + ", clinetType=" + clinetType + ", extend=" + extend
                + ", sessionid=" + sessionId+",status="+status + ", command=" + command +",token="+token+ ", length=" + length + ", data=" + data + ", ip=" + ip + "]";
    }
}