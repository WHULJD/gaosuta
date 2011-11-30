package org.varks.society.local.web.helper;


public class MainImageUploadHelper extends DefaultImageUploadHelper {

	private static final int lowLimitWidth = 250;
	private static final int lowLimitHeight = 250;
	private static final int highLimitWidth = 900;
	private static final int highLimitHeight = 900;
	
	@Override
	protected String getFileName() {
		return photoAlbumId + "." + photoId + "." + "m" + "." + getPostfix();
	}

	@Override
	protected int getLowLimitWidth() {
		return lowLimitWidth;
	}

	@Override
	protected int getLowLimitHeight() {
		return lowLimitHeight;
	}

	@Override
	protected int getHighLimitWidth() {
		return highLimitWidth;
	}

	@Override
	protected int getHighLimitHeight() {
		return highLimitHeight;
	}

}
