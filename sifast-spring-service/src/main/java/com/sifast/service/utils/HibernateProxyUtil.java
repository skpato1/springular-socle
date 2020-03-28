package com.sifast.service.utils;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class HibernateProxyUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(HibernateProxyUtil.class);

	private HibernateProxyUtil() {

	}

	@SuppressWarnings("unchecked")
	public static <T> T initializeAndUnproxy(T entity) {
		if (entity == null) {
			LOGGER.error("Entity passed for initialization is null");
		}

		Hibernate.initialize(entity);
		if (entity instanceof HibernateProxy) {
			return (T) ((HibernateProxy) entity).getHibernateLazyInitializer().getImplementation();
		}
		return entity;
	}
}