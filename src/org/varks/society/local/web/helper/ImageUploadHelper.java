package org.varks.society.local.web.helper;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import org.varks.society.common.context.ContextEnviroment;

public abstract class ImageUploadHelper {
	protected boolean needConvertToDefaultType = false;
	protected MultipartFile file;
	
	protected Long userId;
	protected Long photoAlbumId;
	protected Long photoId;
	
	public void setMultipartFile(MultipartFile file) {
		this.file = file;
	}
	
	public void setNeedConvertToDefaultType(boolean value) {
		needConvertToDefaultType = value;
	}
	
	public String handleUpload() {
		System.out.println(ContextEnviroment.getContextRootPath());
		String relativePath = getUploadImageRativeUrlPath();
		File rootFile = ContextEnviroment.getContextRootFile();
		File file = new File(rootFile, relativePath);
		
		Resource resource = new FileSystemResource(file);
		handleUpload(resource);
		return relativePath;
	}

	protected abstract String getUploadImageRativeUrlPath();
	
	private String handleUpload(Resource dstResource) {
		try {
			byte[] bytes = file.getBytes();
			if(needConvertToDefaultType) 
				bytes = convertToDefaultType(bytes);
			bytes = handleImageBytes(bytes);
			writeToFile(dstResource, bytes);
			
			return dstResource.getFile().getPath();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	//处理上传写入逻辑
	private void writeToFile(Resource dstResource, byte[] bytes) {
		try {
			OutputStream out = new BufferedOutputStream(new FileOutputStream(dstResource.getFile()));
			out.write(bytes);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	protected abstract byte[] handleImageBytes(byte[] bytes);
	
	//如果需要将图片全部转换为默认格式(比如.jpg), 就调用此方法
	private byte[] convertToDefaultType(byte[] bytes) {
		return bytes;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setPhotoAlbumId(Long albumPhotoId) {
		this.photoAlbumId = albumPhotoId;
	}

	public Long getPhotoAlbumId() {
		return photoAlbumId;
	}

	public void setPhotoId(Long photoId) {
		this.photoId = photoId;
	}

	public Long getPhotoId() {
		return photoId;
	}
	
	public String getPostfix() {
		String name = file.getOriginalFilename();
		int k = name.lastIndexOf(".");
		if(k >= 0)
			return name.substring(k + 1);
		else
			return ".jpg";
	}
}
