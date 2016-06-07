package com.ums.umsAdmin.common.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.authentication.AttributePrincipalImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.ums.umsAdmin.common.security.CasUtil;
import com.ums.umsAdmin.common.util.LocaleMessageHelper;

public abstract class BaseController {

	protected String getMessage(String code, Object[] args) {
		return LocaleMessageHelper.getMessage(code, args);
	}
	
	protected String getErrorMessage(HttpServletRequest request, String errorCode) {
		RequestContext requestContext = new RequestContext(request);
		String errorMsg = requestContext.getMessage(errorCode);
		return errorMsg;
	}
	
	protected Map operateResult(String result, String message){
		Map<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("busiStatus", result);
		hashMap.put("message", message);
		return hashMap;
	}
	
	protected Long getLoginUserId(){
		AttributePrincipal principal = (AttributePrincipalImpl) SecurityUtils.getSubject().getPrincipal();
		String userIdStr = CasUtil.getStringAttribute(principal,"userId");
		Long userId = Long.parseLong(userIdStr);
		
		return userId;
	}
}
