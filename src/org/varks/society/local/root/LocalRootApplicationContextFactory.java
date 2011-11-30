package org.varks.society.local.root;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LocalRootApplicationContextFactory {
	private final static ApplicationContext ctxt = new ClassPathXmlApplicationContext(
			"classpath:org/varks/society/local/root/beans.xml");

	public static ApplicationContext getInstance() {
		return ctxt;
	}
	
}
