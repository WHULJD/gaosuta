package org.varks.society.local.web.handlers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.varks.society.local.business.login.ForeLogin;
import org.varks.society.local.business.login.LoginTranslator;
import org.varks.society.local.dao.UserDAO;
import org.varks.society.local.entities.Login;
import org.varks.society.local.entities.User;
import org.varks.society.local.root.LocalApplicationContextFactory;
import org.varks.society.local.web.session.SessionConstants;

@Controller
public class LoginHandler {
	private ApplicationContext daoCtxt;
	private ApplicationContext businessCtxt;

	@SuppressWarnings("unused")
	@PostConstruct
	private void init() {
		daoCtxt = LocalApplicationContextFactory.getDAOApplicationContext();
		businessCtxt = LocalApplicationContextFactory
				.getBusinessApplicationContext();
	}

	/**登录
	 * 
	 * @param userName 用户名
	 * @param password 密码
	 * @return (int) 0: 成功. 1: 失败
	 */
	@RequestMapping("**/login/login.do")
	public String login(HttpSession session, HttpServletResponse response,
			String userName, String password) throws IOException {
		ForeLogin foreLogin = new ForeLogin(userName, password);
		LoginTranslator translator = (LoginTranslator) businessCtxt
				.getBean("loginTranslator");
		Login login = translator.foreToEntity(foreLogin);
		UserDAO dao = (UserDAO) daoCtxt.getBean("userDAO");
		User user = dao.findByLogin(login);
		PrintWriter writer = response.getWriter();
		if (user != null) {
			session.setAttribute(SessionConstants.USER_ID, user.getId());
			writer.print(0);
		} else {
			writer.print(1);
		}

		return "default-view";
	}

	/**退出登录
	 */
	@RequestMapping("**/login/exit.do")
	public String exit(HttpSession session) {
		session.removeAttribute(SessionConstants.USER_ID);

		return "default-view";
	}

	/**更改密码
	 * 
	 * @param oldPassword 原密码
	 * @param newPassword 新密码
	 * @return (int)
	 * 0: 成功; 
	 * 1: 原密码错误; 
	 * 2: 未登录
	 */
	@RequestMapping("**/login/changePassword.do")
	public String changePassword(HttpSession session, HttpServletResponse response, 
			String oldPassword, String newPassword) throws IOException {
		Long id = (Long) session.getAttribute(SessionConstants.USER_ID);
		PrintWriter writer = response.getWriter();
		if(id == null) {
			writer.print(2);
			return "default-view";
		}
		UserDAO dao = (UserDAO) daoCtxt.getBean("userDAO");
		User user = dao.findById(id);
		if(oldPassword.equals(user.getLogin().getPassword())) {
			user.setLogin(new Login(user.getLogin().getUserName(), newPassword));
			dao.update(user);
			writer.print(0);
		}
		else
			writer.print(1);
		return "default-view";
	}
	
	/**检查用户名是否存在.
	 * 
	 * @param userName 用户名
	 * @return (int)
	 * 0: 存在; 
	 * 1: 不存在;
	 */
	@RequestMapping("**/login/checkUserNameExists.do")
	public String checkUserNameExists(HttpServletResponse response, String userName) throws IOException {
		UserDAO dao = (UserDAO) daoCtxt.getBean("userDAO");
		User user = dao.findByUserName(userName);
		PrintWriter writer = response.getWriter();
		if(user != null)
			writer.print(0);
		else
			writer.print(1);
		
		return "default-view";
	}
}
