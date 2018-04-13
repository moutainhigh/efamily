package client;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.winterframework.efamily.base.utils.Base64Util;
     
public class TestImageBinary {      
    static BASE64Encoder encoder = new sun.misc.BASE64Encoder();      
    static BASE64Decoder decoder = new sun.misc.BASE64Decoder();      
          
    public static void main(String[] args) {      
        System.out.println(getImageBinary());      
              
        base64StringToImage(getImageBinary());      
    }      
          
    static String getImageBinary(){      
        File f = new File("C:"+File.separator+"Users"+File.separator+"ibm"+File.separator+"Desktop"+File.separator+"Tulips.jpg");             
        BufferedImage bi;      
        try {      
            bi = ImageIO.read(f);      
            ByteArrayOutputStream baos = new ByteArrayOutputStream();      
            ImageIO.write(bi, "jpg", baos);      
            byte[] bytes = baos.toByteArray();      
            System.out.println(bytes.length); 
            String s=new String(bytes); 
            //return s;
            return encoder.encodeBuffer(bytes).trim();      
        } catch (IOException e) {      
            e.printStackTrace();      
        }      
        return null;      
    }      
          
    static void base64StringToImage(String base64String){      
        try {      
            byte[] bytes1 = decoder.decodeBuffer(base64String);      
                  
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);      
            BufferedImage bi1 =ImageIO.read(bais);      
            File w2 = new File("c://QQ.jpg");//可以是jpg,png,gif格式      
            ImageIO.write(bi1, "jpg", w2);//不管输出什么格式图片，此处不需改动      
        } catch (IOException e) {      
            e.printStackTrace();      
        }      
    }  
    static void base64StringToImage2(String base64String){      
        try {      
            byte[] bytes1 = decoder.decodeBuffer(base64String);      
                   
                FileOutputStream fout = new FileOutputStream("c://QQ.png");
                //将字节写入文件
                fout.write(bytes1);
                fout.close(); 
        } catch (IOException e) {      
            e.printStackTrace();      
        }      
    }       
    static void buff2Image(byte[] b,String mediaType)
    {
    	try{
        FileOutputStream fout = new FileOutputStream("c://QQ."+mediaType);
        //将字节写入文件
        fout.write(b);
        fout.close();
    	}catch(Exception e){
    		
    	}
    }
    static String image2buff(String filePath){
    	try{
    	URL url = new URL(filePath);  
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
	        return Base64Util.getBASE64(out.toByteArray());
		}finally{			
			in.close();
			out.close();        
		}
    	}catch(Exception e){
    		return null;
    	}
    }
    static void toImage(byte[] bytes1){      
        try {       
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);      
            BufferedImage bi1 =ImageIO.read(bais);      
            File w2 = new File("c://QQ.png");//可以是jpg,png,gif格式      
            ImageIO.write(bi1, "jpg", w2);//不管输出什么格式图片，此处不需改动      
        } catch (IOException e) {      
            e.printStackTrace();      
        }      
    }      
     
}  