package org.varks.society.local.web.handlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.varks.society.common.web.edit.HtmlTextShielder;
import org.varks.society.local.dao.RecordDAO;
import org.varks.society.local.entities.Record;
import org.varks.society.local.entities.User;
import org.varks.society.local.root.LocalApplicationContextFactory;
import org.varks.society.local.web.helper.JsonMappingShieldHelper;
import org.varks.society.local.web.session.SessionConstants;

@Controller
public class RecordHandler {
	private ApplicationContext webCtxt;

	@SuppressWarnings("unused")
	@PostConstruct
	private void init() {
		webCtxt = LocalApplicationContextFactory.getWebApplicationContext();
	}

	/** 获取单篇日志信息
	 * 
	 * @param id 日志ID
	 * @return (JSON)
	 * {"state":0,"record":{"id":6,"title":"World","content":"Wo Kao!","generateTime":222222222222222}}
	 * 0: 成功. 1: 失败.
	 */
	@RequestMapping("**/record/find.do")
	public ModelAndView find(Long id) {
		RecordDAO dao = (RecordDAO) webCtxt.getBean("recordDAO");
		Record record = dao.findById(id);
		ModelAndView mav = new ModelAndView("json-view");
		if (record != null) {
			mav.addObject("state", 0);
			JsonMappingShieldHelper.mappedJson(record);
			mav.addObject("record", record);
		} else
			mav.addObject("state", 1);

		return mav;
	}

	/** 获取某用户的日志
	 * 
	 * @param userId 用户ID
	 * @param pageNum 页码
	 * @param pageCapacity 页容量
	 * @return (JSON)
	 * {"records":[{"id":6,"title":"World","content":"Wo Kao!","generateTime":222222222222222,"user":null},{"id":7,"title":"Byebey","content":"I know","generateTime":222222222222222,"user":null}]}
	 */
	@RequestMapping("**/record/findByUserId.do")
	public ModelAndView findAll(Long userId, int pageNum, int pageCapacity) {
		RecordDAO dao = (RecordDAO) webCtxt.getBean("recordDAO");
		List<Record> records = dao.findByUserId(userId, pageNum, pageCapacity);
		for(Record record: records) 
			JsonMappingShieldHelper.mappedJson(record);
		ModelAndView mav = new ModelAndView(SessionConstants.JSON_VIEW);
		mav.addObject("records", records);
		return mav;
	}

	/**查看存储在服务器session用户的日志集合
	 * 
	 * @param pageNum 页码
	 * @param pageCapacity 页容量
	 * @return (JSON)
	 * {"records":[{"id":6,"title":"World","content":"Wo Kao!","generateTime":222222222222222,"user":null},{"id":7,"title":"Byebey","content":"I know","generateTime":222222222222222,"user":null}]}
	 */
	@RequestMapping("**/record/findSelf.do")
	public ModelAndView findAll(HttpSession session, int pageNum,
			int pageCapacity) {
		Long id = (Long) session.getAttribute(SessionConstants.USER_ID);
		return findAll(id, pageNum, pageCapacity);
	}

	/** 登录的用户插入日志. 
	 * 
	 * @param title 日志标题
	 * @param content 日志内容(html格式)
	 * @return (int)
	 * 0: 成功. 1: 未登录. 2: 未知错误.
	 */
	@RequestMapping("**/record/insert.do")
	public String insert(HttpSession session, HttpServletResponse response,
			String title, String content, String[] urls) throws IOException {
		Long id = (Long) session.getAttribute(SessionConstants.USER_ID);
		PrintWriter writer = response.getWriter();

		if (id == null) {
			writer.print(1);
			return SessionConstants.DEFAULT_VIEW;
		}

		RecordDAO dao = (RecordDAO) webCtxt.getBean("recordDAO");
		Record record = new Record();
		record.setTitle(title);
		content = getContent(content, urls);
		record.setContent(content);
		record.setGenerateTime(new Date().getTime());
		User user = new User();
		user.setId(id);
		record.setUser(user);
		Long insertId;
		if ((insertId = dao.insert(record)) != null) {
			writer.print("0:" + insertId);
		}
		else
			writer.print(2);

		return SessionConstants.DEFAULT_VIEW;
	}

	/**登录的用户更新自己的日志
	 * 
	 * @param recordId 日志ID
	 * @param title 标题
	 * @param content 内容
	 * @return (int)
	 * 0: 成功.
	 * 1: 未登录.
	 * 2: 未知错误.
	 */@RequestMapping("**/record/update.do")
	public String update(HttpSession session, HttpServletResponse response,
			Long recordId, String title, String content, String[] urls) throws IOException {
		Long userId = (Long) session.getAttribute(SessionConstants.USER_ID);
		PrintWriter writer = response.getWriter();
		if(userId == null) {
			writer.print(1);
			return SessionConstants.DEFAULT_VIEW;
		}
		
		RecordDAO dao = (RecordDAO) webCtxt.getBean("recordDAO");
		Record record = dao.findById(recordId);
		record.setTitle(title);
		content = getContent(content, urls);
		record.setContent(content);
		if(dao.update(record))
			writer.print(0);
		else
			writer.print(2);
		
		return SessionConstants.DEFAULT_VIEW;
	}

	/**
	 * 删除一篇日志
	 * 
	 * @param id
	 *            日志ID
	 * @return (int) 0: 成功. 1: 未登录. 2: 未知错误.
	 */@RequestMapping("**/record/delete.do")
	public String delete(HttpSession session, HttpServletResponse response, Long id) throws IOException {
		Long userId = (Long) session.getAttribute(SessionConstants.USER_ID);
		PrintWriter writer = response.getWriter();
		if(userId == null) {
			writer.print(1);
			return SessionConstants.DEFAULT_VIEW;
		}
		
		RecordDAO dao = (RecordDAO) webCtxt.getBean("recordDAO");
		if(dao.deleteById(id))
			writer.print(0);
		else
			writer.print(2);
		return SessionConstants.DEFAULT_VIEW;
	}
	 
	 private String getContent(String text, String[] urls) {
		 HtmlTextShielder shielder = (HtmlTextShielder)webCtxt.getBean("htmlTextShielder");
		 text = shielder.shield(text);
		 
		 return getJson(text, urls);
	 }
	 
	 //返回一个json结果.
	 private String getJson(String text, String[] imageUrls) {
		 Content c = new Content();
		 c.setContent(text);
		 c.setUrls(imageUrls);
		 ObjectMapper mapper = new ObjectMapper();
		 try {
			return mapper.writeValueAsString(c);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	 }
	 
	 private static class Content {
		 private String content;
		 private String[] urls;
		 
		 public Content() {}
		 
		public void setContent(String content) {
			this.content = content;
		}
		
		@SuppressWarnings("unused")
		public String getContent() {
			return content;
		}

		public void setUrls(String[] urls) {
			this.urls = urls;
		}

		@SuppressWarnings("unused")
		public String[] getUrls() {
			return urls;
		}
		 
		 
	 }
}
