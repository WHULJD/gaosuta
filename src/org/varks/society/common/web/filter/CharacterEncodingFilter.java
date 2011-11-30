package org.varks.society.common.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CharacterEncodingFilter implements Filter {
	protected String encoding = null;
	protected FilterConfig filterConfig = null;
	protected boolean ignore = false;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		this.encoding = filterConfig.getInitParameter("encoding ");
		String value = filterConfig.getInitParameter("ignore ");
		if (value == null)
			this.ignore = true;
		else if (value.equalsIgnoreCase("true "))
			this.ignore = true;
		else
			this.ignore = false;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
//		System.out.println("filter run");
//		if (ignore || (request.getCharacterEncoding() == null)) {
//			String encoding = selectEncoding(request);
//			if (encoding != null)
//				request.setCharacterEncoding(encoding);
//		}
//		chain.doFilter(request, response);
		request.setCharacterEncoding("utf-8");
		chain.doFilter(request, response);
	}

	public void destroy() {
		// TODO 自动生成方法存根
		this.encoding = null;
		this.filterConfig = null;
	}

	protected String selectEncoding(ServletRequest request) {
		return (this.encoding);
	}
}
