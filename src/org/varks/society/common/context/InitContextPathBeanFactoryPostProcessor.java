package org.varks.society.common.context;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.WebApplicationContext;

public class InitContextPathBeanFactoryPostProcessor implements BeanFactoryPostProcessor, ApplicationContextAware {

	WebApplicationContext ctxt;
	
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory arg0)
			throws BeansException {

//		WebApplicationContext ctxt = (WebApplicationContext)arg0;
//		ContextEnviroment.initContextRootFile(ctxt.getServletContext());

	}
	
	@PostConstruct
	public void init() {
		System.out.println("初始化: HelloWorld");
		ContextEnviroment.initContextRootFile(ctxt.getServletContext());
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		this.ctxt = (WebApplicationContext)arg0;
	}

}
