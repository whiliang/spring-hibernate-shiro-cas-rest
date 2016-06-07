package com.ums.umsAdmin.common.util;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

public final class LocaleMessageHelper {
	private static ReloadableResourceBundleMessageSource messageSource;
	
	public static String getMessage(String code, Object[] args){
//		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
//				.getRequestAttributes()).getRequest();
//		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
//		Locale locale = localeResolver.resolveLocale(request);
		return messageSource.getMessage(code, args, Locale.CHINA);
	}

	public ReloadableResourceBundleMessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(
			ReloadableResourceBundleMessageSource messageSource) {
		LocaleMessageHelper.messageSource = messageSource;
	}
}
