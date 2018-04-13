package com.winterframework.efamily.base.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import com.winterframework.efamily.base.enums.ResourceType;

public class FileAccess {

	public static boolean move(File srcFile, String destPath) {
		// Destination directory
		File dir = new File(destPath);

		// Move file to new directory
		boolean success = srcFile.renameTo(new File(dir, srcFile.getName()));

		return success;
	}

	public static boolean move(String srcFile, String destPath) {
		// File (or directory) to be moved
		File file = new File(srcFile);

		// Destination directory
		File dir = new File(destPath);

		// Move file to new directory
		boolean success = file.renameTo(new File(dir, file.getName()));

		return success;
	}

	public static void copy(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) {
				InputStream inStream = new FileInputStream(oldPath);
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				int length;
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread;
					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			System.out.println("error  ");
			e.printStackTrace();
		}
	}

	public static void copy(File oldfile, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			// File oldfile = new File(oldPath);
			if (oldfile.exists()) {
				InputStream inStream = new FileInputStream(oldfile);
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread;
					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			System.out.println("error  ");
			e.printStackTrace();
		}
	}
	public static void scaleImage(String sourceImagePath, String oriName){
		String lowPath = sourceImagePath.replace(ResourceType.DefinitionType.HIGH, ResourceType.DefinitionType.LOW);
		String middlePath = sourceImagePath.replace(ResourceType.DefinitionType.HIGH, ResourceType.DefinitionType.MIDDLE);

			double size = ImageHelper.getFileSize(sourceImagePath)/1000;
			if(size<35){
				ImageHelper.scaleImageWithParams(sourceImagePath, lowPath, oriName, 1f);
			}else{
				ImageHelper.scaleImageWithParams(sourceImagePath, lowPath, oriName, 0.9f);
				size = ImageHelper.getFileSize(lowPath)/1000;
				int i=1;
				while(size>30){
					ImageHelper.scaleImageWithParams(sourceImagePath, lowPath, oriName, (float)(1-(i*0.1)));
					size = ImageHelper.getFileSize(lowPath)/1000;
					i++;
				}
			}

			size = ImageHelper.getFileSize(sourceImagePath)/1000;
			if(size<100){
				ImageHelper.scaleImageWithParams(sourceImagePath, middlePath, oriName, 1f);
			}else{
				ImageHelper.scaleImageWithParams(sourceImagePath, middlePath, oriName, 0.9f);
				size = ImageHelper.getFileSize(middlePath)/1000;
				int j=1;
				while(size>80){
					ImageHelper.scaleImageWithParams(sourceImagePath, middlePath, oriName, (float)(1-(j*0.1)));
					size = ImageHelper.getFileSize(middlePath)/1000;
					j++;
				}
			}
		
	}
}
