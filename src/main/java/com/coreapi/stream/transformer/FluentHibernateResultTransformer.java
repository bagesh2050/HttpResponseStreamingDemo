package com.coreapi.stream.transformer;

import org.hibernate.transform.BasicTransformerAdapter;

import com.coreapi.stream.transformer.InternalUtils.ClassUtils;

public class FluentHibernateResultTransformer extends BasicTransformerAdapter {

	private static final long serialVersionUID = 6825154815776629666L;

	private final Class<?> resultClass;

	private NestedSetter[] setters;

	public FluentHibernateResultTransformer(Class<?> resultClass) {
		this.resultClass = resultClass;
	}

	@Override
	public Object transformTuple(Object[] tuple, String[] aliases) {
		createCachedSetters(resultClass, aliases);

		Object result = ClassUtils.newInstance(resultClass);

		for (int i = 0; i < aliases.length; i++) {
			setters[i].set(result, tuple[i]);
		}

		return result;
	}

	private void createCachedSetters(Class<?> resultClass, String[] aliases) {
		if (setters == null) {
			setters = createSetters(resultClass, aliases);
		}
	}

	private static NestedSetter[] createSetters(Class<?> resultClass, String[] aliases) {
		NestedSetter[] result = new NestedSetter[aliases.length];

		for (int i = 0; i < aliases.length; i++) {
			result[i] = NestedSetter.create(resultClass, aliases[i]);
		}

		return result;
	}

}
