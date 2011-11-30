package org.varks.society.local.web.helper;

import org.springframework.web.multipart.MultipartFile;

public class ImageUploadHelperFactory {
	public static ImageUploadHelper getMainImageUploadHelper(
			MultipartFile file, Long userId, Long albumId, Long photoId) {
		ImageUploadHelper helper = new MainImageUploadHelper();
		handleHelper(helper, file, userId, albumId, photoId);

		return helper;
	}

	public static ImageUploadHelper getSmallImageUploadHelper(
			MultipartFile file, Long userId, Long albumId, Long photoId) {
		ImageUploadHelper helper = new SmallImageUploadHelper();
		handleHelper(helper, file, userId, albumId, photoId);

		return helper;
	}

	private static void handleHelper(ImageUploadHelper helper,
			MultipartFile file, Long userId, Long albumId, Long photoId) {
		helper.setMultipartFile(file);
		helper.setPhotoAlbumId(albumId);
		helper.setUserId(userId);
		helper.setPhotoId(photoId);
	}
}
