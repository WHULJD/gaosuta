package org.varks.society.local.web.helper;

import java.io.File;

import org.varks.society.common.context.ContextEnviroment;
import org.varks.society.common.image.DefaultImageBytesSizeScaler;
import org.varks.society.common.image.ImageBytesSizeScaler;
import org.varks.society.common.image.ImageBytesWrapper;
import org.varks.society.common.image.ImageBytesWrapperFactory;

public abstract class DefaultImageUploadHelper extends ImageUploadHelper {
	private ImageBytesSizeScaler scaller = new DefaultImageBytesSizeScaler();
	
	@Override
	protected String getUploadImageRativeUrlPath() {
		String parentDirectoryString = getParentDirectoryPath();
		File rootFile = ContextEnviroment.getContextRootFile();
		File parentFile = new File(rootFile, parentDirectoryString);
		if(!parentFile.exists())
			parentFile.mkdirs();
		String fileName = getFileName();
		return parentDirectoryString +  fileName;
	}
	
	protected abstract String getFileName();
	
	private String getParentDirectoryPath() {
		
		return "Resources/Local/Users/Images/";
	}
	
	@Override
	protected byte[] handleImageBytes(byte[] bytes) {
		ImageBytesWrapper wrapper = ImageBytesWrapperFactory.getInstance(bytes);
		int originalWidth = wrapper.getWidth();
		int originalHeight = wrapper.getHeight();
		int highLimitWidth = getHighLimitWidth();
		int highLimitHeight = getHighLimitHeight();
		if(originalWidth * originalHeight > highLimitWidth * highLimitHeight) {
			int limitWidth = highLimitWidth;
			int limitHeight = (int)(highLimitWidth * ((double)originalHeight / originalWidth));
			if(limitHeight < getLowLimitHeight())
				limitHeight = getLowLimitHeight();
			return reduceImage(bytes, limitWidth, limitHeight);
		}
		else
			return bytes;
	}
	
	private byte[] reduceImage(byte[] bytes, int limitWidth, int limitHeight) {
		return scaller.scale(bytes, limitWidth, limitHeight);
	}

	protected abstract int getLowLimitWidth();
	
	protected abstract int getLowLimitHeight();
	
	protected abstract int getHighLimitWidth();
	
	protected abstract int getHighLimitHeight();
}
