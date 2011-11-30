package org.varks.society.common.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**关于反射的一些工具方法: 例如
 * <li><ol>获取指定类的所有域</ol><ol>获取指定类的所有方法</ol></li>
 * @author lenovo
 *
 */
public class ReflectUtils {
	public static List<Field> getAllFields(Class<?> clazz) {
		List<Field> list = new ArrayList<Field>();
		getAllFields(clazz, list);
		return list;
	}
	
	public static List<Method> getAllMethods(Class<?> clazz) {
		return getAllMethods(clazz, false);
	}
	
	public static List<Method> getAllMethods(Class<?> clazz, boolean containObject) {
		List<Method> list = new ArrayList<Method>();
		getAllMethods(clazz, list);
		if(containObject)
			list.addAll(Arrays.asList(Object.class.getDeclaredMethods()));
		return list;
	}
	
	private static void getAllFields(Class<?> clazz, List<Field> list) {
		if(clazz.equals(Object.class))
			return;
		
		Field[] fields = clazz.getDeclaredFields();
		list.addAll(Arrays.asList(fields));
		
		getAllFields(clazz.getSuperclass(), list);
	}
	
	private static void getAllMethods(Class<?> clazz, List<Method> list) {
		if(clazz.equals(Object.class))
			return;
		
		Method[] method = clazz.getDeclaredMethods();
		list.addAll(Arrays.asList(method));
		
		getAllMethods(clazz.getSuperclass(), list);
	}

}
