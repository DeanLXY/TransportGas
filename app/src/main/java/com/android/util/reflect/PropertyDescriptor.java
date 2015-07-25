package com.android.util.reflect;

import java.lang.reflect.Method;

public class PropertyDescriptor {
	private String name;
	private String displayName;
	private Method readMethod;
	private Method writeMethod;
	private Object obj;

	public PropertyDescriptor() {
	}

	public PropertyDescriptor(String fieldName, Object receiver) {
		if (fieldName == null || receiver == null) {
			throw new NullPointerException("PropertyDescriptor:fieldName or Object is null");
		}
		this.obj = receiver;
		parsePropertyDescriptor(fieldName, receiver);
	}

	public void parsePropertyDescriptor(String fieldName, Object receiver) {
		if (fieldName == null || receiver == null) {
			throw new NullPointerException("PropertyDescriptor:fieldName or Object is null");
		}
		this.obj = receiver;
		setName(fieldName);
		setDisplayName(fieldName);
		setReadMethod(PropertyDescriptorHelper.getReadMethod(obj.getClass(), fieldName));
		setWriteMethod(PropertyDescriptorHelper.getWriteMethod(obj.getClass(), fieldName));
	}

	public void setPropertyValue(Object... value) throws Exception {
		Method writeMethod = getWriteMethod();
		if (writeMethod == null || this.obj == null) {
			return;
		}
		readMethod.invoke(this.obj, value);
	}

	public Object getPropertyValue() throws Exception {
		Object result = null;
		Method readMethod = getReadMethod();
		if (readMethod == null || this.obj == null) {
			return null;
		}
		result = readMethod.invoke(this.obj);
		return result;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Method getReadMethod() {
		return readMethod;
	}

	public void setReadMethod(Method readMethod) {
		this.readMethod = readMethod;
	}

	public Method getWriteMethod() {
		return writeMethod;
	}

	public void setWriteMethod(Method writeMethod) {
		this.writeMethod = writeMethod;
	}
}
