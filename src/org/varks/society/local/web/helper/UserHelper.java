package org.varks.society.local.web.helper;

import javax.servlet.http.HttpSession;

import org.varks.society.local.dao.UserDAO;
import org.varks.society.local.entities.User;
import org.varks.society.local.web.session.SessionConstants;

public class UserHelper {
	private UserDAO userDAO;
	
	public UserHelper() {}
	
	public User getUserBySession(HttpSession session) {
		Long id = (Long) session.getAttribute(SessionConstants.USER_ID);
		User user = userDAO.findById(id);
		return user;
	}
	
	public void isolateUser(User user) {
		user.setPhotoAlbums(null);
		user.setRecords(null);
	}

	public void setUserDAO(UserDAO dao) {
		this.userDAO = dao;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}
}
