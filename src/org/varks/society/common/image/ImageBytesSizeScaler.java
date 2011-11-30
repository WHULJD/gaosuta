package org.varks.society.common.image;

public interface ImageBytesSizeScaler {
	byte[] scale(byte[] srcBytes, int newWidth, int newHeight);
}
