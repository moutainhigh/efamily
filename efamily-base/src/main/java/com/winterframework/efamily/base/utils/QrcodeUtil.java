package com.winterframework.efamily.base.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

@Component("qrcodeUtil")
public class QrcodeUtil {
	private static final Logger log = LoggerFactory.getLogger(QrcodeUtil.class);
	 
	// 图像宽度
	public static final int WIDTH = 240;
	// 图像高度
	public static final int HEIGHT = 240;  
	
	public static String generate(String filePath,String extType,String content){
		if(null==filePath || null==content ) return null; 
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();  
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put( EncodeHintType.MARGIN, 0);
        BitMatrix bitMatrix = null;
        try {
			bitMatrix = new MultiFormatWriter().encode(content,  
			        BarcodeFormat.QR_CODE, QrcodeUtil.WIDTH, QrcodeUtil.HEIGHT, hints); 
			File destFile = new File(filePath);
			if (!destFile.getParentFile().exists()) {
				destFile.getParentFile().mkdirs();
			}
			MatrixToImageWriter.writeToFile(bitMatrix, extType, destFile);
			return destFile.getAbsolutePath();
		} catch (WriterException e) {
			log.error("Error occurs when save QR Code file",e);
		}catch (IOException e) {
			log.error("Error occurs when save QR Code file",e);
		}
		return null;
	}
	
	public static void main(String[] a){
		generate("D:\\222", "jpg", "http://www.ejialian365.com/qr?imei=22222");
	}

}