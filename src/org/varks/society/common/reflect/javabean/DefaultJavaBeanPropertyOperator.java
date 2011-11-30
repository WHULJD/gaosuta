package org.varks.society.common.reflect.javabean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.varks.society.common.reflect.ReflectUtils;

public class DefaultJavaBeanPropertyOperator<E> implements
		JavaBeanPropertyOperator<E> {
	private Class<E> beanClass;
	private final Map<String, Method> getMethods = new HashMap<String, Method>();
	private final Map<String, Method> setMethods = new HashMap<String, Method>();

	@Override
	public void setJavaBeanClass(Class<E> beanClass) {
		this.beanClass = beanClass;
		List<Method> methods = ReflectUtils.getAllMethods(beanClass);
		for (Method method : methods)
			handleMethod(method);
	}

	@Override
	public Class<E> getJavaBeanClass() {
		return beanClass;
	}

	@Override
	public <T> T getProperty(E bean, String name, Class<T> clazz) {
		String methodName = (!clazz.equals(Boolean.class)) ? JavaBeanNamingUtils
				.getGetMethod(name) : JavaBeanNamingUtils.getIsMethod(name);
		Method method = getMethods.get(methodName);
		if (method != null)
			try {
				return clazz.cast(method.invoke(bean));
			} catch (IllegalArgumentException e) {
				throw new JavaBeanOperateException(
						JavaBeanOperateException.JAVA_BEAN_GETTER_HAS_PARAMETERS
								+ name, e);
			} catch (IllegalAccessException e) {
				throw new JavaBeanOperateException(
						JavaBeanOperateException.JAVA_BEAN_METHODS_NOT_PUBLIC
								+ name, e);
			} catch (InvocationTargetException e) {
				throw new JavaBeanOperateException(e);
			} catch (ClassCastException ex) {
				throw new JavaBeanOperateException("The return value of the java bean is not the class : " + clazz, ex);
			}
		else
			throw new JavaBeanOperateException(
					JavaBeanOperateException.JAVA_BEAN_NO_SUCH_GETTER + name);
	}

	@Override
	public void setProperty(E bean, String name, Object value) {
		String methodName = JavaBeanNamingUtils.getSetMethod(name);
		Method method = setMethods.get(methodName);
		if (method != null)
			try {
				method.invoke(bean, value);
			} catch (IllegalArgumentException e) {
				throw new JavaBeanOperateException(
						JavaBeanOperateException.JAVA_BEAN_SETTER_HAS_ILLEGAL_PARAMETERS
								+ name, e);
			} catch (IllegalAccessException e) {
				throw new JavaBeanOperateException(
						JavaBeanOperateException.JAVA_BEAN_METHODS_NOT_PUBLIC
								+ name, e);
			} catch (InvocationTargetException e) {
				throw new JavaBeanOperateException(e);
			}
		else
			throw new JavaBeanOperateException(
					JavaBeanOperateException.JAVA_BEAN_NO_SUCH_SETTER + name);
	}

	private void handleMethod(Method method) {
		String methodName = method.getName();
		if (JavaBeanNamingUtils.isGetter(methodName))
			getMethods.put(methodName, method);
		else if (JavaBeanNamingUtils.isSetter(methodName))
			setMethods.put(methodName, method);
	}

}
