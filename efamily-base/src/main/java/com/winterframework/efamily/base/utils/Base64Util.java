
package com.winterframework.efamily.base.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64Util
{
    public Base64Util(){}
    public static String getBASE64(byte bytes[]){
        if(bytes == null){
            return null;
        }else{
            return (new BASE64Encoder()).encode(bytes);
        }
    }
    public static String getBASE64FromFile(String fileName) throws IOException{
        if(fileName == null){
            return null;
        }
        InputStream input = null;
        try{
            input = new FileInputStream(fileName);
            byte bytes[] = new byte[input.available()];
            input.read(bytes);
            String s= getBASE64(bytes);
            return s;
        }catch(IOException e){
            throw e;
        }finally{
        	if(input != null){
                input.close();
            }
        }
    }

    public static byte[] getBytesFromBASE64(String s){
        if(s == null){
            return null;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try{
            byte b[] = decoder.decodeBuffer(s);
            return b;
        }catch(Exception e){
            return null;
        }
    }

    public static void main(String args[])
    {
        String content = "just for test!";
        String base64Str = getBASE64(content.getBytes());
        System.out.println((new StringBuilder("encode to base64 :\n")).append(base64Str).toString());
        System.out.println();
        String retStr = new String(getBytesFromBASE64(base64Str));
        System.out.println((new StringBuilder("decode from base64:\n")).append(retStr).toString());
    }
}


/*
	DECOMPILATION REPORT

	Decompiled from: C:\Users\ibm\Downloads\GETUI_SERVER_SDK\GETUI_SERVER_SDK\资源文件\gexin-rp-sdk-base-4.0.0.1.jar
	Total time: 164 ms
	Jad reported messages/errors:
Couldn't fully decompile method getBASE64FromFile
Couldn't resolve all exception handlers in method getBASE64FromFile
	Exit status: 0
	Caught exceptions:
*/