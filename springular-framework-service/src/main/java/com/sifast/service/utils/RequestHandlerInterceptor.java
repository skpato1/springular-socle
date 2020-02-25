package com.sifast.service.utils;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.LocaleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

@ControllerAdvice
public class RequestHandlerInterceptor extends HandlerInterceptorAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(RequestHandlerInterceptor.class);

	/**
	 * In this case intercept the request BEFORE it reaches the controller
	 */
	private static HttpServletRequest request;

	private String currentLanguage;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		boolean accepted;
		try {
			LOGGER.info("Intercepting: {}", request.getRequestURI());
			LOGGER.info("language: {}", request.getHeader("language"));
			currentLanguage = request.getHeader("language");
			LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
			localeResolver.setLocale(request, response, LocaleUtils.toLocale(currentLanguage));
			setRequest(request);
			accepted = true;
		} catch (Exception e) {
			LOGGER.error("RequestHandlerInterceptor", e);
			accepted = false;
		}
		return accepted;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public static void setRequest(HttpServletRequest request) {
		RequestHandlerInterceptor.request = request;
	}

	public String getCurrentLanguage() {
		return currentLanguage;
	}

	public void setCurrentLanguage(String currentLanguage) {
		this.currentLanguage = currentLanguage;
	}

	public String getMessage(String key) {
		try {
			if (!key.isEmpty()) {
				Locale locale;
				if ("ar".equals(currentLanguage)) {
					locale = new Locale("ar");
				} else {
					locale = RequestContextUtils.getLocale(request);
				}
				ClassLoader loader = Thread.currentThread().getContextClassLoader();
				ResourceBundle bundle = ResourceBundle.getBundle("message", locale, loader);
				return bundle.getString(key);
			}
		} catch (Exception e) {
			LOGGER.error("Translate key {} error {}", key, e.getMessage());

		}
		return key;
	}

}
