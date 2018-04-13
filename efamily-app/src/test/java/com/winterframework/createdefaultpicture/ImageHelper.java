package com.winterframework.createdefaultpicture;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import javax.imageio.ImageIO;

/*** 
 * 对图片进行操作 
 *  
 * @author chenzheng_java 
 * @since 2015/1/16
 *  
 */
public class ImageHelper {

	private static ImageHelper imageHelper = null;

	public static ImageHelper getImageHelper() {
		if (imageHelper == null) {
			imageHelper = new ImageHelper();
		}
		return imageHelper;
	}
	
	
	public static Long getFileSize(String path){
		FileChannel fc= null;  
		FileInputStream fis= null;
	    try {  
	        File f= new File(path);  
	        if (f.exists() && f.isFile()){  
	            fis= new FileInputStream(f);  
	            fc= fis.getChannel();  
	           return fc.size();
	        }else{  
	            return 0l;
	        }  
	    } catch (FileNotFoundException e) {  
	        return 0l;
	    } catch (IOException e) {  
	        return 0l;
	    } finally {  
	        if (null!=fc){  
	            try{  
	                fc.close();
	            }catch(IOException e){  
	                return 0l;
	            }  
	        } 
	        if (null!=fis){  
	            try{  
	            	fis.close();
	            }catch(IOException e){  
	                return 0l;
	            }  
	        }   
	    }  
	}

	/*** 
	 * 将图片缩放到指定的高度或者宽度 
	 * @param sourceImagePath 图片源地址 
	 * @param destinationPath 压缩完图片的地址 
	 * @param width 缩放后的宽度 
	 * @param height 缩放后的高度 
	 * @param auto 是否自动保持图片的原高宽比例 
	 * @param format 图图片格式 例如 jpg 
	 */
	public static void scaleImageWithParams(String sourceImagePath, String destinationPath, String oriName,float scale) {
		String fileName = sourceImagePath;
		String targetFileName = destinationPath;
		File file = new File(fileName);
		BufferedImage src;
		FileOutputStream out = null;
		try {
			src = ImageIO.read(file);
			int width = src.getWidth();
			int height = src.getHeight();
			 int targetSizeWidth = Integer.valueOf(Math.round(width * scale));
		     int targetSizeHeight = Integer.valueOf(Math.round(height * scale));
			BufferedImage temp = new BufferedImage(targetSizeWidth, targetSizeHeight, BufferedImage.TYPE_INT_RGB);
			temp.getGraphics().drawImage(src, 0, 0, targetSizeWidth, targetSizeHeight, null);
			/*out = new FileOutputStream(targetFileName);
			com.sun.image.codec.jpeg.JPEGImageEncoder encoder = com.sun.image.codec.jpeg.JPEGCodec.createJPEGEncoder(out);
			encoder.encode(temp);*/
			try{
			File fileTarget = new File(targetFileName);
			fileTarget.delete();}catch(Exception e){
				System.out.println("delete image error.");
			}
			ImageIO.write(temp, oriName, new File(targetFileName));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	 /**
     * <b>function:</b> 通过指定的比例和图片对象，返回一个放大或缩小的宽度、高度
     * @author hoojo
     * @createDate 2012-2-7 上午10:27:59
     * @param scale 缩放比例
     * @param image 图片对象
     * @return 返回宽度、高度
     */
    public static int[] getSize(float scale, BufferedImage image) {
        int targetWidth = image.getWidth(null);
        int targetHeight = image.getHeight(null);
        long standardWidth = Math.round(targetWidth * scale);
        long standardHeight = Math.round(targetHeight * scale);
        return new int[] { Integer.parseInt(Long.toString(standardWidth)), Integer.parseInt(String.valueOf(standardHeight)) };
    }
	
	public static String getExtension(String s) {
		String ext = null;
		int i = s.lastIndexOf('.');
		if (i > 0 && i < s.length() - 1) {
			ext = s.substring(i + 1);
		}
		return ext;
	}

	public static String getName(String s) {
		String ext = null;
		int i = s.lastIndexOf('.');
		if (i > 0 && i < s.length() - 1) {
			ext = s.substring(0, i);
		}
		return ext;
	}

}