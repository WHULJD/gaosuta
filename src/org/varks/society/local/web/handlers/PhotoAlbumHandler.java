package org.varks.society.local.web.handlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.varks.society.common.web.StateCodes;
import org.varks.society.local.dao.PhotoAlbumDAO;
import org.varks.society.local.dao.UserDAO;
import org.varks.society.local.entities.PhotoAlbum;
import org.varks.society.local.entities.User;
import org.varks.society.local.root.LocalApplicationContextFactory;
import org.varks.society.local.web.helper.JsonMappingShieldHelper;
import org.varks.society.local.web.session.SessionConstants;

@Controller
public class PhotoAlbumHandler {
	private ApplicationContext daoCtxt;
	
	@SuppressWarnings("unused")
	@PostConstruct
	private void init() {
		daoCtxt = LocalApplicationContextFactory.getDAOApplicationContext();
	}
	
	
	/** 创建相册
	 * 
	 * @param title 标题
	 * @param description 描述
	 * @return (int)
	 * 0:成功; 1: 未登录; 2: 未知错误
	 */@RequestMapping("**/photoAlbum/insert.do")
	public String create(HttpSession session, HttpServletResponse response,
			String title, String description) throws IOException {
		Long userId = (Long)session.getAttribute(SessionConstants.USER_ID);
		PrintWriter writer = response.getWriter();
		if(userId == null) {
			writer.print(StateCodes.NOT_LOGIN);
			return SessionConstants.DEFAULT_VIEW;
		}
		
		PhotoAlbum album = new PhotoAlbum();
		album.setTitle(title);
		album.setDescription(description);
		album.setGenerateTime(new Date().getTime());
		UserDAO userDao = (UserDAO)daoCtxt.getBean("userDAO");
		PhotoAlbumDAO albumDao = (PhotoAlbumDAO)daoCtxt.getBean("photoAlbumDAO");
		User user = userDao.findById(userId);
		album.setUser(user);
		if(albumDao.insert(album) == null) {
			writer.print(StateCodes.UNKNOWN);
			return SessionConstants.DEFAULT_VIEW;
		}
		
		writer.print(StateCodes.SUCCESS);
		return SessionConstants.DEFAULT_VIEW;
	}

	/**删除相册
	 * 
	 * @param id 相册id
	 * @return (int)
	 * 0, 成功; 1, 未登录; 2.未知错误
	 */@RequestMapping("**/photoAlbum/delete.do")
	public String delete(HttpSession session, HttpServletResponse response,
			Long id) throws IOException {
		Long userId = (Long)session.getAttribute(SessionConstants.USER_ID);
		PrintWriter writer = response.getWriter();
		if(userId == null) {
			writer.print(StateCodes.NOT_LOGIN);
			return SessionConstants.DEFAULT_VIEW;
		}
		
		PhotoAlbumDAO albumDao = (PhotoAlbumDAO)daoCtxt.getBean("photoAlbumDAO");
		if(!albumDao.deleteById(id)) {
			writer.print(StateCodes.UNKNOWN);
			return SessionConstants.DEFAULT_VIEW;
		}
		writer.print(StateCodes.SUCCESS);
		return SessionConstants.DEFAULT_VIEW;
	}

	/**更新相册基本内容
	 * 
	 * @param id 相册ID
	 * @param title 相册标题
	 * @param description 相册描述
	 * @return (int)
	 * 0, 成功; 1, 未登录; 2.未知错误
	 */@RequestMapping("**/photoAlbum/update.do")
	public String update(HttpSession session, HttpServletResponse response,
			Long id, @RequestParam(required=false)String title, @RequestParam(required=false)String description) throws IOException {
		Long userId = (Long)session.getAttribute(SessionConstants.USER_ID);
		PrintWriter writer = response.getWriter();
		if(userId == null) {
			writer.print(StateCodes.NOT_LOGIN);
			return SessionConstants.DEFAULT_VIEW;
		}
		
		PhotoAlbumDAO albumDao = (PhotoAlbumDAO)daoCtxt.getBean("photoAlbumDAO");
		PhotoAlbum album = albumDao.findById(id);
		if(title!=null){
			album.setTitle(title);
		}
		if(description!=null){
			album.setDescription(description);
		}
		
		
		if(albumDao.update(album))
			writer.print(StateCodes.SUCCESS);
		else
			writer.print(StateCodes.UNKNOWN);
		
		return SessionConstants.DEFAULT_VIEW;
	}

	/**获取相册基本内容
	 * 
	 * @param id 相册ID
	 * @return (josn)
	 */@RequestMapping("**/photoAlbum/find.do")
	public ModelAndView find(Long id) {
		PhotoAlbumDAO dao = (PhotoAlbumDAO)daoCtxt.getBean("photoAlbumDAO");
		PhotoAlbum album = dao.findById(id);
		ModelAndView mav = new ModelAndView(SessionConstants.JSON_VIEW);
		if(album == null) {
			mav.addObject("state", StateCodes.UNKNOWN);
		}
		else {
			mav.addObject("state", StateCodes.SUCCESS);
			JsonMappingShieldHelper.mappedJson(album);
			mav.addObject("album", album);
		}
		return mav;
	}

}
