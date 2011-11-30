package org.varks.society.local.web.handlers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.varks.society.local.business.login.ForeLogin;
import org.varks.society.local.business.login.LoginTranslator;
import org.varks.society.local.dao.UserDAO;
import org.varks.society.local.entities.Login;
import org.varks.society.local.entities.User;
import org.varks.society.local.root.LocalApplicationContextFactory;
import org.varks.society.local.web.helper.JsonMappingShieldHelper;
import org.varks.society.local.web.helper.UserHelper;
import org.varks.society.local.web.session.SessionConstants;

@Controller
public class UserHandler {
	private ApplicationContext daoCtxt;
	private ApplicationContext businessCtxt;
	private ApplicationContext webCtxt;
	private UserHelper helper;

	@SuppressWarnings("unused")
	@PostConstruct
	private void init() {
		daoCtxt = LocalApplicationContextFactory.getDAOApplicationContext();
		businessCtxt = LocalApplicationContextFactory
				.getBusinessApplicationContext();
		webCtxt = LocalApplicationContextFactory.getWebApplicationContext();
		helper = (UserHelper) webCtxt.getBean("userHelper");
	}

	/**
	 * 获取登录的用户信息
	 * 
	 * @return (JSON)
	 *         {"state":0,"user":{"id":2,"login":{"userName":"hello","password"
	 *         :"world"},"name":"byebye","photo":(byte[])}} state: 0, 成功; 1,
	 *         未登录; 2.未知错误
	 */
	@RequestMapping("**/user/find.do")
	public ModelAndView find(HttpSession session) {
		ModelAndView mv = new ModelAndView("json-view");
		Long id = (Long) session.getAttribute(SessionConstants.USER_ID);
		if (id == null) {
			mv.addObject("state", 1);
			return mv;
		}

		UserDAO dao = (UserDAO) daoCtxt.getBean("userDAO");
		User user = dao.findById(id);
		helper.isolateUser(user);
		mv.addObject("state", 0);
		JsonMappingShieldHelper.mappedJson(user);
		mv.addObject("user", user);
		return mv;
	}

	/**
	 * 插入用户(注册)
	 * 
	 * @param userName
	 *            用户名
	 * @param password
	 *            密码
	 * @param nickName
	 *            昵称
	 * @return (int): 0: 成功; 1: 失败
	 */
	@RequestMapping("**/user/insert.do")
	public String insert(MultipartHttpServletRequest request,
			HttpServletResponse response, String userName, String password,
			String nickName) throws IOException {
		User user = new User();
		ForeLogin foreLogin = new ForeLogin(userName, password);
		LoginTranslator translator = (LoginTranslator) businessCtxt
				.getBean("loginTranslator");
		Login login = translator.foreToEntity(foreLogin);
		user.setLogin(login);
		user.setName(nickName);

		MultipartFile file = request.getFile("headPhoto");
		byte[] fileBytes = (file == null) ? null : file.getBytes();
		user.setPhoto(fileBytes);

		UserDAO dao = (UserDAO) daoCtxt.getBean("userDAO");
		Long id = dao.insert(user);
		PrintWriter writer = response.getWriter();
		if (id != null)
			writer.print(0);
		else
			writer.print(1);

		return "default-view";
	}

	/**
	 * 用户信息更新
	 * 
	 * @param nickName
	 *            昵称
	 * @return (int) 0: 成功. 1: 未登录. 2: 未知错误.
	 */
	@RequestMapping("**/user/update.do")
	public String update(HttpSession session, HttpServletResponse response,
			@RequestParam(required=false) String nickName) throws IOException {
		Long id = (Long) session.getAttribute(SessionConstants.USER_ID);
		if (id == null) {
			response.getWriter().print(1);
			return "default-view";
		}

		UserDAO dao = (UserDAO) daoCtxt.getBean("userDAO");
		User user = dao.findById(id);
		if ((nickName != null))
			user.setName(nickName);
		if (dao.update(user))
			response.getWriter().print(0);
		else
			response.getWriter().print(1);

		return "default-view";

	}
}
