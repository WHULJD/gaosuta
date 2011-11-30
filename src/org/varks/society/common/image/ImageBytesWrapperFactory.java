package org.varks.society.common.image;

public class ImageBytesWrapperFactory {
	public static ImageBytesWrapper getInstance(byte[] bytes) {
		return new DefaultImageBytesWrapper(bytes);
	}
}
