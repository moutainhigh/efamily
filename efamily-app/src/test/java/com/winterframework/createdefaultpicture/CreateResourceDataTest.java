package com.winterframework.createdefaultpicture;

import java.io.File;
import javax.annotation.Resource;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import com.winterframework.efamily.base.enums.ResourceType;
import com.winterframework.efamily.base.enums.YesNo;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.utils.ResourceUtils;
import com.winterframework.efamily.entity.EfResourceAssist;
import com.winterframework.efamily.entity.FmlResource;
import com.winterframework.efamily.service.IEfComResourceAssistService;
import com.winterframework.efamily.service.IResourceService;
import com.winterframework.modules.test.BaseTestCase;

public class CreateResourceDataTest extends BaseTestCase {
	Logger log = LoggerFactory.getLogger(CreateResourceDataTest.class);

	@Resource(name = "efComResourceAssistServiceImpl")
	private IEfComResourceAssistService efComResourceAssistServiceImpl;

	@Resource(name = "resourceServiceImpl")
	private IResourceService resourceServiceImpl;
	
	
	
	@Test
	@Rollback(false)
	public void test() throws Exception {
		//---------------【注意修改db.properties配置文件，指定需要生成的数据存放到那个数据库】
		//----------------------------------------------------------------------------
		int isMulti = 1;// 0 不生成压缩文件， 1生成压缩文件
		int resourceAssistType = 1;// 1 默认头像： 2默认天气 （参考数据库为准）
		
		//**** 本地存放和处理完成以后的地址 【注意：生成以后的文件需要拷贝到服务器上】
		String dirFilePath = "D:\\image\\source\\weather\\";
		String dirFilePathDest = "D:\\image\\dest\\";
		
		//**** 服务器上和数据库中记录的地址
		String dataAndServicePath = "/opt/nginx/html/init/image/";
		//----------------------------------------------------------------------------
		
		
		String extType = "";
		String type = "image"; // 
		
		File filePathDir = new File(dirFilePath);
		filePathDir.mkdirs();
		filePathDir = new File(dirFilePathDest);
		filePathDir.mkdirs();
		
		//*****************  读取文件[在一个目录下的所有文件]   ****************************************
		File dirFile = new File(dirFilePath);
		File[] dirFileArr = dirFile.listFiles();
		for (int i = 0; i < dirFileArr.length; i++) {
			File dirFileTemp = dirFileArr[i];
			String filePath = dirFileTemp.getName();
			
			extType=filePath.substring(filePath.indexOf(".")+1);
			
			String filePathOrigin=filePath.substring(0,filePath.indexOf("."));
			String filePathHigh=filePath.substring(0,filePath.indexOf("."))+ResourceType.DefinitionType.HIGH;

			//*****************  移动文件[high] *******************************************************
			FileAccess.copy(dirFilePath+filePath, dirFilePathDest+filePathHigh);
			
			//*****************  压缩文件[low  middle] ************************************************
			if(YesNo.YES.getValue()==isMulti){
				//*** 生成low middle 文件 
	        	scaleImage(dirFilePathDest+filePathHigh,extType);
	        }
			
			//*****************  保存到数据库[ef_resource  ef_resource_assist] *************************
			Context ctx = new Context();
			ctx.set("userId", "-1");
			ctx.set("deviceId", "1001");
			
			String resourceId = ResourceUtils.getMediaId(type);
			FmlResource resource = new FmlResource();
			resource.setResourceId(resourceId);
			resource.setExtType(extType);
			resource.setType(type);
			resource.setFilePath(dataAndServicePath+filePathOrigin);
			resource.setIsMulti(isMulti);
			resourceServiceImpl.save(ctx, resource);
			
			EfResourceAssist efResourceAssist = new EfResourceAssist();
			efResourceAssist.setResourceId(resourceId);
			efResourceAssist.setType(resourceAssistType);
			efResourceAssist.setStatus(0);
			
			efComResourceAssistServiceImpl.save(ctx, efResourceAssist);
		}
	}
	
	private void scaleImage(String sourceImagePath, String oriName){
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