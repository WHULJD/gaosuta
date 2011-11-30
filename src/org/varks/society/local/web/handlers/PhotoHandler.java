package org.varks.society.local.web.handlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.varks.society.common.web.StateCodes;
import org.varks.society.local.dao.PhotoAlbumDAO;
import org.varks.society.local.dao.PhotoDAO;
import org.varks.society.local.entities.Photo;
import org.varks.society.local.entities.PhotoAlbum;
import org.varks.society.local.root.LocalApplicationContextFactory;
import org.varks.society.local.web.helper.ImageUploadHelper;
import org.varks.society.local.web.helper.ImageUploadHelperFactory;
import org.varks.society.local.web.helper.JsonMappingShieldHelper;
import org.varks.society.local.web.session.SessionConstants;

@Controller
public class PhotoHandler {
	private ApplicationContext webCtxt;

	@SuppressWarnings("unused")
	@PostConstruct
	private void init() {
		webCtxt = LocalApplicationContextFactory.getWebApplicationContext();
	}

	/**
	 * 用户插入图片到相册
	 * 
	 * @param request
	 * @param photoAlbumId
	 * @param title
	 * @param description
	 * @return (JSON)
	 */
	@RequestMapping("**/photo/insert.do")
	public String insert(HttpSession session, HttpServletRequest request,
			HttpServletResponse response, Long photoAlbumId, String title,
			String description) throws IOException {
		PrintWriter writer = response.getWriter();

		Long userId = (Long) session.getAttribute(SessionConstants.USER_ID);
		if (userId == null) {
			writer.write(StateCodes.NOT_LOGIN);
			return SessionConstants.DEFAULT_VIEW;
		}

		MultipartHttpServletRequest multipartRequest = null;
		try {
			multipartRequest = (MultipartHttpServletRequest) request;
		} catch (ClassCastException ex) {
			ex.printStackTrace();
			writer.write(StateCodes.UPLODA_NOT_FORM_DATA);
			return SessionConstants.DEFAULT_VIEW;
		}

		// 照片这块
		Photo photo = new Photo();
		photo.setTitle(title);
		photo.setDescription(description);
		PhotoAlbum photoAlbum = new PhotoAlbum();
		photoAlbum.setId(photoAlbumId);
		photo.setPhotoAlbum(photoAlbum);
		// 先存起来
		PhotoDAO dao = (PhotoDAO) webCtxt.getBean("photoDAO");
		Long photoId = dao.insert(photo);
		if (photoId == null) {
			writer.write(StateCodes.UNKNOWN);
			return SessionConstants.DEFAULT_VIEW;
		}

		// 处理上传
		MultipartFile multipartFile = multipartRequest.getFile("uploadImage");
		ImageUploadHelper mainHelper = ImageUploadHelperFactory
				.getMainImageUploadHelper(multipartFile, userId, photoAlbumId,
						photoId);
		ImageUploadHelper smallHelper = ImageUploadHelperFactory
				.getSmallImageUploadHelper(multipartFile, userId, photoAlbumId,
						photoId);
		String mainUrl = null;
		String smallUrl = null;
		try {
			mainUrl = mainHelper.handleUpload();
			smallUrl = smallHelper.handleUpload();
		} catch (Exception ex) {
			ex.printStackTrace();
			writer.write(StateCodes.UPLOAD_ERROR);
			dao.deleteById(photoId);
			return SessionConstants.DEFAULT_VIEW;
		}

		photo = dao.findById(photoId);
		photo.setUrl(mainUrl);
		photo.setPreviewUrl(smallUrl);

		if (dao.update(photo)) {
			writer.write(StateCodes.SUCCESS);
			return SessionConstants.DEFAULT_VIEW;
		} else {
			writer.write(StateCodes.UNKNOWN);
			return SessionConstants.DEFAULT_VIEW;
		}

	}

	@RequestMapping("**/photo/delete.do")
	public String delete(HttpSession session, HttpServletResponse response,
			Long id) throws IOException {

		PrintWriter writer = response.getWriter();

		Long userId = (Long) session.getAttribute(SessionConstants.USER_ID);
		if (userId == null) {
			writer.write(StateCodes.NOT_LOGIN);
			return SessionConstants.DEFAULT_VIEW;

		}
		PhotoDAO dao = (PhotoDAO) webCtxt.getBean("photoDAO");
		boolean a = dao.deleteById(id);
		if (a) {
			writer.write(StateCodes.SUCCESS);
			return SessionConstants.DEFAULT_VIEW;

		} else {
			writer.write(StateCodes.UNKNOWN);
			return SessionConstants.DEFAULT_VIEW;

		}

	}

	/**
	 * 通过照片的id拿到图片.
	 * return (json)
	 * {"state":0,"photo":{"id":1,"title":"fds","generateTime"
	 * :64352345432532532,"description":"fds","previewUrl":
	 * "fdsaf","photoAlbum":null,"image":"fdsa"}}
	 */
	@RequestMapping("**/photo/findByPhotoId.do")
	public ModelAndView findByPhotoId(HttpSession session,
			HttpServletRequest request, HttpServletResponse response,
			Long photoId) throws IOException {
		ModelAndView mv = new ModelAndView("json-view");
		Long id = (Long) session.getAttribute(SessionConstants.USER_ID);
		if (id == null) {
			mv.addObject("state", 1);
			return mv;
		}
		PhotoDAO dao = (PhotoDAO) webCtxt.getBean("photoDAO");
		Photo photo=dao.findById(photoId);
	   
	    mv.addObject("state", 0);
		JsonMappingShieldHelper.mappedJson(photo);
		mv.addObject("photo", photo);
		return mv;
	}
	
	/**
	 *通过相册的id拿到照片
	 *return(json)
	 *
	 *{"photos":[{"id":1,"title":"fds",
	 *"generateTime":64352345432532532,"
	 *description":"fds",
	 *"previewUrl":"fdsaf",
	 *"photoAlbum":null,"image":"fdsa"},
	 *{"id":5,
	 *"title":"nin",
	 *"generateTime":0,
	 *"description":"aa ",
	 *"previewUrl":"Resources/Local/Users/Images/1.5.s.jpg",
	 *"photoAlbum":null,
	 *"image":"Resources/Local/Users/Images/1.5.m.jpg"}],"state":0}
	 *
	 */
	
	
	@RequestMapping("**/photo/findByPhotoAlbumId.do")
	public ModelAndView findByPhotoAlbumId(HttpSession session,
			HttpServletRequest request, HttpServletResponse response,
			Long photoAlbumId,int pageNumber,int pageCapacity) throws IOException {
		ModelAndView mv = new ModelAndView("json-view");
		Long id = (Long) session.getAttribute(SessionConstants.USER_ID);
		if (id == null) {
			mv.addObject("state", 1);
			return mv;
		}
		PhotoDAO dao = (PhotoDAO) webCtxt.getBean("photoDAO");
      	List<Photo> photos=dao.findByPhotoAlbumId(photoAlbumId, pageNumber, pageCapacity);
      	mv.addObject("state", 0);
	    for(Photo photo:photos)JsonMappingShieldHelper.mappedJson(photo);
		mv.addObject("photos", photos);
		return mv;
		
	}
}