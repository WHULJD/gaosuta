package org.varks.society.common.reflect.javabean;

/**Java bean的一些名称规范辅助方法
 * 
 * @author lenovo
 *
 */
public class JavaBeanNamingUtils {
	public static String getGetMethod(String property) {
		property = firstAlphaToUpper(property);
		return "get" + property;
	}
	
	public static String getIsMethod(String property) {
		property = firstAlphaToUpper(property);
		return "is" + property;
	}
	
	public static String getSetMethod(String property) {
		property = firstAlphaToUpper(property);
		return "set" + property;
	}
	
	public static boolean isGetMethod(String method) {
		return isStartsWithAndNextAlphaUpper(method, "get");
	}
	
	public static boolean isIsMethod(String method) {
		return isStartsWithAndNextAlphaUpper(method, "is");
	}
	
	public static boolean isSetMethod(String method) {
		return isStartsWithAndNextAlphaUpper(method, "set");
	}
	
	public static boolean isGetter(String method) {
		return isGetMethod(method) || isIsMethod(method);
	}
	
	public static boolean isSetter(String method) {
		return isSetMethod(method);
	}
	
	private static String firstAlphaToUpper(String str) {
		return Character.toUpperCase(str.charAt(0)) + str.substring(1);
	}
	
//	private static String firstAlphaToLower(String str) {
//		return Character.toLowerCase(str.charAt(0)) + str.substring(1);
//	}
	
	private static boolean isStartsWithAndNextAlphaUpper(String method, String start) {
		return method.startsWith(start) && Character.isUpperCase(method.charAt(start.length()));
	}
}
