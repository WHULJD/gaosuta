package org.varks.society.common.reflect.javabean;

@SuppressWarnings("serial")
public class JavaBeanOperateException extends RuntimeException {
	public static final String JAVA_BEAN_GETTER_HAS_PARAMETERS = "Java bean getter should not has any parameters: ";
	public static final String JAVA_BEAN_METHODS_NOT_PUBLIC = "Java bean getter or setter should be public: ";
	public static final String JAVA_BEAN_NO_SUCH_GETTER = "Java bean no such property or getter: ";
	public static final String JAVA_BEAN_SETTER_HAS_ILLEGAL_PARAMETERS = "Java bean setter should only has a single parameter: ";
	public static final String JAVA_BEAN_NO_SUCH_SETTER = "Java bean no such property or setter: ";
	
	public JavaBeanOperateException(String msg) {
		super(msg);
	}

	public JavaBeanOperateException(Throwable ex) {
		super(ex);
	}

	public JavaBeanOperateException(String msg, Throwable ex) {
		super(msg, ex);
	}
}
