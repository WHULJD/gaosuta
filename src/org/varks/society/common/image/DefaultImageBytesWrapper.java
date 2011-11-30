package org.varks.society.common.image;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class DefaultImageBytesWrapper implements ImageBytesWrapper {
	private long length;
	private BufferedImage image;
	
	public DefaultImageBytesWrapper(byte[] bytes) {
		length = bytes.length;
		InputStream in = new ByteArrayInputStream(bytes);
		try {
			image = ImageIO.read(in);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int getWidth() {
		return image.getWidth();
	}

	@Override
	public int getHeight() {
		return image.getHeight();
	}

	@Override
	public long getLength() {
		return length;
	}
	
	
}
