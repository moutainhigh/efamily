package com.winterframework.efamily.base.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util{
    public MD5Util(){}
    public static String getMD5Format(String data){
        try{
            MessageDigest message = (MessageDigest)messageDigestHolder.get();
            if(message == null){
                message = MessageDigest.getInstance("MD5");
                messageDigestHolder.set(message);
            }
            message.update(data.getBytes());
            byte b[] = message.digest();
            String digestHexStr = "";
            for(int i = 0; i < 16; i++){
                digestHexStr = (new StringBuilder(String.valueOf(digestHexStr))).append(byteHEX(b[i])).toString();
            }
            return digestHexStr;
        }catch(Exception e){
            throw new RuntimeException(new StringBuilder("MD5\u683C\u5F0F\u5316\u65F6\u53D1\u751F\u5F02\u5E38[{}]: {}").toString(),e);
        }
    }

    public static String getMD5Format(byte data[]){
        try{
            MessageDigest message = (MessageDigest)messageDigestHolder.get();
            if(message == null){
                message = MessageDigest.getInstance("MD5");
                messageDigestHolder.set(message);
            }
            message.update(data);
            byte b[] = message.digest();
            String digestHexStr = "";
            for(int i = 0; i < 16; i++){
                digestHexStr = (new StringBuilder(String.valueOf(digestHexStr))).append(byteHEX(b[i])).toString();
            }
            return digestHexStr;
        }catch(Exception e){
            return null;
        }
    }

    private static String byteHEX(byte ib){
        char ob[] = new char[2];
        ob[0] = hexDigits[ib >>> 4 & 15];
        ob[1] = hexDigits[ib & 15];
        String s = new String(ob);
        return s;
    }

    private static ThreadLocal messageDigestHolder;
    static final char hexDigits[] = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
        'a', 'b', 'c', 'd', 'e', 'f'
    };

    static{
        messageDigestHolder = new ThreadLocal();
        try{
            MessageDigest message = MessageDigest.getInstance("MD5");
            messageDigestHolder.set(message);
        }catch(NoSuchAlgorithmException e){
            throw new RuntimeException((new StringBuilder("\u521D\u59CB\u5316java.security.MessageDigest\u5931\u8D25:")).toString(), e);
        }
    }
}