/**   
* @Title: EjlFileUploadServiceImpl.java 
* @Package com.winterframework.efamily.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-5-12 上午11:01:19 
* @version V1.0   
*/
package com.winterframework.efamily.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.Selectors;
import org.apache.commons.vfs2.cache.DefaultFilesCache;
import org.apache.commons.vfs2.impl.DefaultFileSystemManager;
import org.apache.commons.vfs2.provider.local.DefaultLocalFileProvider;
import org.apache.commons.vfs2.provider.sftp.SftpFileProvider;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;
import org.apache.commons.vfs2.provider.zip.ZipFileProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.efamily.service.IEjlFileUploadService;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/** 
* @ClassName: EjlFileUploadServiceImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-5-12 上午11:01:19 
*  
*/
@Service("ejlFileUploadServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class EjlFileUploadServiceImpl implements IEjlFileUploadService {

	@PropertyConfig(value = "imageupload.url")
	private String imageuploadPath;

	private final static DefaultFileSystemManager mgr = getDefaultFileSystemManager();

	/**
	* Title: uploadByteArrays
	* Description:
	* @param type 1图片 2声音  3视频
	* @param name
	* @param datas
	 * @throws IOException 
	* @throws Exception 
	* @see com.winterframework.efamily.service.IEjlFileUploadService#uploadByteArrays(java.lang.String, java.lang.String, java.lang.Byte[]) 
	*/
	@Override
	public String uploadByteArrays(String type, byte[] datas) throws IOException {
		String fileName = DigestUtils.md5Hex(datas) + new Date().getTime() + "_" + type + ".txt";
		File file = new File(fileName);
		BufferedOutputStream out;
		try {
			out = new BufferedOutputStream(new FileOutputStream(file));
			try {
				out.write(datas);
				out.flush();
				FileObject localFile = mgr.resolveFile(file.getAbsolutePath());
				FileSystemOptions opts = new FileSystemOptions();
				SftpFileSystemConfigBuilder sscb = SftpFileSystemConfigBuilder.getInstance();
				sscb.setStrictHostKeyChecking(opts, "no");
				sscb.setUserDirIsRoot(opts, false);
				FileObject ftpFile = mgr.resolveFile(imageuploadPath, opts);
				if (!ftpFile.exists()) {
					ftpFile.createFolder();
				}
				ftpFile.copyFrom(localFile, Selectors.SELECT_ALL);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				out.close();
				file.deleteOnExit();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			file.deleteOnExit();
		}
		return fileName;

	}

	private static DefaultFileSystemManager getDefaultFileSystemManager() {

		DefaultFileSystemManager mgr = new DefaultFileSystemManager();
		// SFTP 供应者
		SftpFileProvider fp = new SftpFileProvider();

		// ZIP 供应者
		ZipFileProvider zp = new ZipFileProvider();
		// 缺省本地文件供应者
		DefaultLocalFileProvider lf = new DefaultLocalFileProvider();

		try {
			// common-vfs 中 文件管理器的使用范例
			mgr.addProvider("sftp", fp);
			mgr.addProvider("zip", zp);
			mgr.addProvider("file", lf);
			mgr.setFilesCache(new DefaultFilesCache());
			mgr.init();

		} catch (FileSystemException e) {
			e.printStackTrace();
		}
		return mgr;
	}
}
