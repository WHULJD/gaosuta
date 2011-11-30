package org.varks.society.common.context;

import java.io.File;

import javax.servlet.ServletContext;

public class ContextEnviroment {
	private static File rootFile;
	
	public static File getContextRootFile() {
		return rootFile;
	}
	
	public static String getContextRootPath() {
		return rootFile.getPath();
	}
	
	public static void initContextRootFile(ServletContext ctxt) {
		rootFile = new File(ctxt.getRealPath("/"));
		
		System.out.println(rootFile.getPath());
	}
}
