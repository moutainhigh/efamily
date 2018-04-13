package com.winterframework.createdefaultpicture;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.winterframework.efamily.base.enums.ResourceType;
import com.winterframework.efamily.base.model.Context;
import com.winterframework.efamily.base.utils.FileAccess;
import com.winterframework.efamily.base.utils.QrcodeUtil;
import com.winterframework.efamily.base.utils.ResourceUtils;
import com.winterframework.efamily.entity.EfCustomer;
import com.winterframework.efamily.entity.EjlDevice;
import com.winterframework.efamily.entity.FmlResource;
import com.winterframework.efamily.entity.Qrcode;
import com.winterframework.efamily.service.IEfComCustomerService;
import com.winterframework.efamily.service.IEfComResourceAssistService;
import com.winterframework.efamily.service.IEjlDeviceService;
import com.winterframework.efamily.service.IQrcodeService;
import com.winterframework.efamily.service.IResourceService;
import com.winterframework.modules.test.BaseTestCase;

public class CreateQrcodeResourceIdTest extends BaseTestCase {
	Logger log = LoggerFactory.getLogger(CreateResourceDataTest.class);

	@Resource(name = "efComResourceAssistServiceImpl")
	private IEfComResourceAssistService efComResourceAssistServiceImpl;

	@Resource(name = "resourceServiceImpl")
	private IResourceService resourceServiceImpl;

	@Resource(name="efComCustomerServiceImpl")
	private IEfComCustomerService efComCustomerServiceImpl;
	
	@Resource(name = "qrcodeServiceImpl")
	private IQrcodeService qrcodeServiceImpl; 
	
	@Resource(name="ejlDeviceServiceImpl")
	private IEjlDeviceService ejlDeviceServiceImpl;
	
	
	public static final String serverFilePath = "/opt/nginx/html/";
	public static final String localFilePath = "D:\\qrcode";
	
	private String getFilePath(String type){  
		final String FILE_SUBFIXX = "";
		String fileName=System.currentTimeMillis()+"";
		return  File.separator+type + File.separator + "qrcode" + File.separator  + fileName + FILE_SUBFIXX;
	}
	

	@Test
	@Rollback(false)
	public void test() throws Exception {
		
		//****** 查询ef_qrcode表拿到所有的设备
		Context ctxT = new Context();
		ctxT.set("userId", "-1");
		ctxT.set("deviceId", "1001");
		Qrcode qrcodeParam = new Qrcode();
		List<Qrcode> qrcodeList = qrcodeServiceImpl.selectListObjByAttribute(ctxT, qrcodeParam);
		
		for(Qrcode qrcode:qrcodeList){
			
			 //****** 更新ef_qrcode ejl_device的二维码关联表
			 EjlDevice ejlDevice = new EjlDevice();
			 ejlDevice.setCode(qrcode.getImei());
			 System.out.println("qrcode.getImei() : "+qrcode.getImei());
			 EjlDevice ejlDeviceData = ejlDeviceServiceImpl.selectOneObjByAttribute(ctxT, ejlDevice);
			 if(ejlDeviceData==null){//***
				 System.out.println("ejlDeviceData = "+ejlDeviceData);
				 continue;
			 }
			
			//****** 根据customer获取download_url 
			 EfCustomer customer = new EfCustomer();
			 customer.setNumber(qrcode.getCustomerNumber());
             customer = efComCustomerServiceImpl.selectOneObjByAttribute(ctxT, customer);
             System.out.println("customer: "+customer.toString());
			 String qrcodeUrl = customer.getQrcodeUrl();
			 String imageType = "png";
			 String resourceType = "image";
			 String filePath = getFilePath(resourceType);
			 String fileName = System.currentTimeMillis()+"";
			 String filePathServer=serverFilePath+resourceType + "/" + "qrcode" + "/"  + fileName ;
			 String filePathLocal =localFilePath+filePath;
			 //将内容生成二维码，返回二维码的绝对路径
			 QrcodeUtil.generate(filePathLocal, imageType, qrcodeUrl+"?imei="+qrcode.getImei());
			 
			 //*** 把图片压缩成大中小
			 //*****************  复制文件[high] *******************************************************
			 FileAccess.copy(filePathLocal, filePathLocal+ResourceType.DefinitionType.HIGH);
			
			 //*****************  压缩文件[low  middle] ************************************************
			 FileAccess.scaleImage(filePathLocal+ResourceType.DefinitionType.HIGH,"jpg");
			 
			 //****** 存储到resource表
			 String resourceId=ResourceUtils.getMediaId(resourceType);
			 
			 FmlResource resource=new FmlResource();
			 resource.setResourceId(resourceId);
			 resource.setType(resourceType);
			 resource.setExtType("jpg");
			 resource.setFilePath(filePathServer);
			 resource.setIsMulti(0);
			 resource.setRemark("手动生成的二维码资源数据");
			 resourceServiceImpl.save(ctxT, resource);
			 
			 ejlDeviceData.setQrcodeResourceId(resourceId);
			 ejlDeviceServiceImpl.save(ctxT, ejlDeviceData);
			 
			 qrcode.setQrcodeResourceId(resourceId);
			 qrcodeServiceImpl.save(ctxT, qrcode);
			
			 //********TODO 把生成的二维码图片移动到服务器上对应的目录
		}
		
	}

}