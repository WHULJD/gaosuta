package org.varks.society.local.root;

import org.springframework.context.ApplicationContext;

public class LocalApplicationContextFactory {
	public static ApplicationContext getDAOApplicationContext() {
		return getApplicationContextByBeanName("daoApplicationContext");
	}
	
	
	public static ApplicationContext getBusinessApplicationContext() {
		return getApplicationContextByBeanName("businessApplicationContext");
	}
	
	
	public static ApplicationContext getWebApplicationContext() {
		return getApplicationContextByBeanName("webApplicationContext");
	}
	

	private static ApplicationContext getApplicationContextByBeanName(
			String beanId) {
		return (ApplicationContext) LocalRootApplicationContextFactory
				.getInstance().getBean(beanId);
	}
	
}
