package com.android.util.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PropertyDescriptorHelper {

	public static List<PropertyDescriptor> getPropertyDescriptors(Class<?> type) {
		List<PropertyDescriptor> lsDescriptor = new ArrayList<PropertyDescriptor>();
		Method[] aryMethod = type.getMethods();
		Map<String, Method> dicMethod = new HashMap<String, Method>();
		for (Method method : aryMethod) {
			if (method.getName().startsWith("set") && method.getParameterTypes().length == 1) {
				dicMethod.put(method.getName(), method);
			}
		}

		for (Method method : aryMethod) {
			if (method.getParameterTypes().length != 0) {
				continue;
			}
			if (method.getName().startsWith("get")) {
				String name = Character.toLowerCase(method.getName().charAt(3)) + method.getName().substring(4, method.getName().length());

				PropertyDescriptor desc = new PropertyDescriptor();
				desc.setDisplayName(name);
				desc.setName(name);
				desc.setReadMethod(method);
				String setMethodName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1, name.length());
				if (dicMethod.containsKey(setMethodName)) {
					desc.setWriteMethod(dicMethod.get(setMethodName));
				}
				lsDescriptor.add(desc);
			} else if (method.getName().startsWith("is")) {
				String name = Character.toLowerCase(method.getName().charAt(2)) + method.getName().substring(3, method.getName().length());
				PropertyDescriptor desc = new PropertyDescriptor();
				desc.setDisplayName(name);
				desc.setName(name);
				desc.setReadMethod(method);
				String setMethodName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1, name.length());
				if (dicMethod.containsKey(setMethodName)) {
					desc.setWriteMethod(dicMethod.get(setMethodName));
				}
				lsDescriptor.add(desc);
			}
		}

		return lsDescriptor;
	}

	public static List<PropertyDescriptor> getPropertyDescriptors(Object bean) {
		return getPropertyDescriptors(bean.getClass());
	}

	public static String getReadMethodName(String fieldName) {
		if (fieldName == null || fieldName.length() == 0) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		sb.append("get");
		sb.append(fieldName.substring(0, 1).toUpperCase());
		sb.append(fieldName.substring(1));
		String setMethodName = sb.toString();
		return setMethodName;
	}

	public static String getReadMethodName2(String fieldName) {
		if (fieldName == null || fieldName.length() == 0) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		sb.append("is");
		sb.append(fieldName.substring(0, 1).toUpperCase());
		sb.append(fieldName.substring(1));
		String setMethodName = sb.toString();
		return setMethodName;
	}

	public static String getWriteMethodName(String fieldName) {
		if (fieldName == null || fieldName.length() == 0) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		sb.append("set");
		sb.append(fieldName.substring(0, 1).toUpperCase());
		sb.append(fieldName.substring(1));

		String getMethodName = sb.toString();
		return getMethodName;
	}

	/**
	 * java反射bean的get方法
	 * 
	 * @param objectClass
	 * @param fieldName
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public static Method getReadMethod(Class objectClass, String fieldName) {
		String setMethodName = getReadMethodName(fieldName);
		String setMethodName2 = getReadMethodName2(fieldName);
		try {
			Method readMethod = objectClass.getMethod(setMethodName);
			if (readMethod == null) {
				readMethod = objectClass.getMethod(setMethodName2);
			}
			return readMethod;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * java反射bean的set方法
	 * 
	 * @param objectClass
	 * @param fieldName
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public static Method getWriteMethod(Class objectClass, String fieldName) {
		try {
			Class[] parameterTypes = new Class[1];
			Field field = objectClass.getDeclaredField(fieldName);
			parameterTypes[0] = field.getType();
			StringBuffer sb = new StringBuffer();
			sb.append("set");
			sb.append(fieldName.substring(0, 1).toUpperCase());
			sb.append(fieldName.substring(1));
			Method method = objectClass.getMethod(sb.toString(), parameterTypes);
			return method;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 执行set方法
	 * 
	 * @param o执行对象
	 * @param fieldName属性
	 * @param value值
	 */

	public static void invokeSet(Object o, String fieldName, Object value) throws Exception {
		Method method = getWriteMethod(o.getClass(), fieldName);
		if (method == null) {
			throw new RuntimeException("no such mothod for " + fieldName);
		}
		try {
			method.invoke(o, new Object[] { value });
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 执行get方法
	 * 
	 * @param o执行对象
	 * @param fieldName属性
	 */

	public static Object invokeGet(Object o, String fieldName) throws Exception {
		Method method = getReadMethod(o.getClass(), fieldName);
		if (method == null) {
			return null;
		}
		try {
			method.setAccessible(true);
			return method.invoke(o, (Object[]) null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}