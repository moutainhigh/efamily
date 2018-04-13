/*
 * Copyright 2011 Hxrainbow, Co.ltd. All rights reserved.
 * 
 * FileName: JsonUtil.java
 * 
 */

package com.quartz.monitor.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * 转换为json的公用类
 *
 * @author denny
 * @version 1.0
 * @created 2015-11-30 下午01:18:41
 * @history 
 * @see
 */
public class FileUtil {
	private static Logger log = Logger.getLogger(FileUtil.class);
	 /**
     * 功能：Java读取txt文件的内容
     * 步骤：1：先获得文件句柄
     * 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
     * 3：读取到输入流后，需要读取生成字节流
     * 4：一行一行的输出。readline()。
     * 备注：需要考虑的是异常情况
     * @param filePath
     */
	public static List<String> readLongString (String filePath) throws Exception{
		List<String> stringList = new  ArrayList<String>();
        String encoding="GBK";
        File file=new File(filePath);
        if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                Pattern pattern = Pattern.compile("[0-9]*");
                Matcher isNum = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                    if(!StringUtils.isEmpty(lineTxt)) {
                        isNum = pattern.matcher(lineTxt);
                        //如果是数字，添加
                        if(isNum.matches()) {
                        	stringList.add(lineTxt);
                        }
                    }
                }
                read.close();
        }
        return stringList;
	}
	
	 /**
     * 功能：Java读取txt文件的内容
     * 步骤：1：先获得文件句柄
     * 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
     * 3：读取到输入流后，需要读取生成字节流
     * 4：一行一行的输出。readline()。
     * 备注：需要考虑的是异常情况
     * @param filePath
     */
	public static List<String> readLongString (InputStream fileInputStream) throws Exception{
		List<String> stringList = new  ArrayList<String>();
        String encoding="GBK";
//        File file=new File(filePath);
        if(fileInputStream != null ){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                fileInputStream,encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                Pattern pattern = Pattern.compile("[0-9]*");
                Matcher isNum = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                    if(!StringUtils.isEmpty(lineTxt)) {
                        isNum = pattern.matcher(lineTxt);
                        //如果是数字，添加
                        if(isNum.matches()) {
                        	stringList.add(lineTxt);
                        }
                    }
                }
                read.close();
        }
        return stringList;
	}
}
