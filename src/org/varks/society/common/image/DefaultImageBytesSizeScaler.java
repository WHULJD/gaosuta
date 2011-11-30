package org.varks.society.common.image;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class DefaultImageBytesSizeScaler implements ImageBytesSizeScaler {

	@Override
	public byte[] scale(byte[] srcBytes, int newWidth, int newHeight) {
		InputStream input = new ByteArrayInputStream(srcBytes);
		BufferedImage srcImage;
		try {
			srcImage = ImageIO.read(input);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		Image tempImage = srcImage.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT);
		BufferedImage dstImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
		Graphics g = dstImage.getGraphics();
		g.drawImage(tempImage, 0, 0, null);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try {
			ImageIO.write(dstImage, "JPEG", output);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return output.toByteArray();
	}

}
