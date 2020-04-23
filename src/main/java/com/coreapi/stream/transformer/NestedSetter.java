package com.coreapi.stream.transformer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.hibernate.PropertyAccessException;

import com.coreapi.stream.transformer.InternalUtils.ClassUtils;

public final class NestedSetter {

	private final Class<?> clazz;

	private final Method[] getMethods;

	private final Method[] setMethods;

	private final Method method;

	private final String propertyName;

	private NestedSetter(Class<?> clazz, Method[] getMethods, Method[] setMethods, Method method, String propertyName) {
		this.clazz = clazz;
		this.method = method;
		this.propertyName = propertyName;
		this.getMethods = getMethods;
		this.setMethods = setMethods;
	}

	public void set(Object target, Object value) {
		try {
			invokeSet(target, value);
		} catch (Exception ex) {
			checkForPrimitive(value);
			String errorMessage = String.format("Setter information: expected type: %s, actual type: %s.",
					method.getParameterTypes()[0].getName(), value == null ? null : value.getClass().getName());
			throw new PropertyAccessException(ex, errorMessage, true, clazz, propertyName);
		}
	}

	private void checkForPrimitive(Object value) {
		if (value == null && method.getParameterTypes()[0].isPrimitive()) {
			throw new PropertyAccessException(null, "Value is null, but property type is primitive.", true, clazz,
					propertyName);
		}
	}

	private void invokeSet(Object target, Object value) throws IllegalAccessException, InvocationTargetException {
		Object tmpTarget = target;
		for (int i = 0; i < getMethods.length; i++) {
			Object tmpTarget2 = getMethods[i].invoke(tmpTarget, "");
			if (tmpTarget2 == null) {
				tmpTarget2 = ClassUtils.newInstance(getMethods[i].getReturnType());
				setMethods[i].invoke(tmpTarget,  tmpTarget2 );
			}
			tmpTarget = tmpTarget2;
		}
		method.invoke(tmpTarget, value);
	}

	/**
	 * Create a setter for a nested property.
	 */
	public static NestedSetter create(Class<?> theClass, String propertyName) {
		NestedSetter result = getSetterOrNull(theClass, propertyName);
		if (result == null) {
			throw new PropertyAccessException(null, "Could not find a setter for a nested property.", true, theClass,
					propertyName);
		}
		return result;
	}

	private static NestedSetter getSetterOrNull(Class<?> theClass, String propertyName) {
		if (theClass == Object.class || theClass == null || propertyName == null) {
			return null;
		}

		String[] propertyParts = ReflectionUtils.getPropertyParts(propertyName);

		int nestedCount = propertyParts.length;

		Method[] getMethods = new Method[nestedCount - 1];
		Method[] setMethods = new Method[nestedCount - 1];

		Class<?> currentClass = theClass;
		for (int i = 0; i < nestedCount - 1; i++) {
			Method getter = ReflectionUtils.getClassGetter(currentClass, propertyParts[i]);

			if (getter == null) {
				throw new PropertyAccessException(null,
						String.format("Intermediate getter property not found for nesetd property `%s`", propertyName),
						false, theClass, propertyParts[i]);
			}

			getMethods[i] = getter;
			setMethods[i] = ReflectionUtils.getClassSetter(currentClass, propertyParts[i], getter);

			currentClass = getMethods[i].getReturnType();
		}

		Method method = setterMethod(currentClass, propertyParts[nestedCount - 1]);
		if (method != null) {
			ReflectionUtils.makePublic(method);
			return new NestedSetter(theClass, getMethods, setMethods, method, propertyName);
		}

		NestedSetter setter = getSetterOrNull(theClass.getSuperclass(), propertyName);
		if (setter == null) {
			Class<?>[] interfaces = theClass.getInterfaces();
			for (int i = 0; setter == null && i < interfaces.length; i++) {
				setter = getSetterOrNull(interfaces[i], propertyName);
			}
		}

		return setter;
	}

	private static Method setterMethod(Class<?> theClass, String propertyName) {
		Method getter = ReflectionUtils.getClassGetter(theClass, propertyName);
		return ReflectionUtils.getClassSetter(theClass, propertyName, getter);
	}

}
