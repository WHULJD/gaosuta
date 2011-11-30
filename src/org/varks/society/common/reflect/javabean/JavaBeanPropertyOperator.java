package org.varks.society.common.reflect.javabean;

/**Java bean属性操作, 有设置属性和获取属性.
 * 
 * @author lenovo
 *
 * @param <E>
 */
public interface JavaBeanPropertyOperator<E> {
	<T> T getProperty(E bean, String name, Class<T> clazz);
	void setProperty(E bean, String name, Object value);
	
	void setJavaBeanClass(Class<E> beanClass);
	Class<E> getJavaBeanClass();
}
