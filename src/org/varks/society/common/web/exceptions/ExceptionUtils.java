package org.varks.society.common.web.exceptions;

import javax.servlet.jsp.JspWriter;

public class ExceptionUtils {
	public static void write(Throwable ex, JspWriter out) {
		try {
			if(ex == null)
				return;
			
			out.println(ex);
			
			StackTraceElement[] traces =  ex.getStackTrace();
			for(StackTraceElement trace: traces) {
				out.println(trace);
			}
			
			write(ex.getCause(), out);
		} catch(Exception catchedEx) {
			throw new RuntimeException(catchedEx);
		}
	}
}
