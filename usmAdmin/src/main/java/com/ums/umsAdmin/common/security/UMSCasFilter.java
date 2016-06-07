package com.ums.umsAdmin.common.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.cas.CasFilter;
import org.jasig.cas.client.authentication.AttributePrincipal;

public class UMSCasFilter extends CasFilter {
	
	@Override
	protected AuthenticationToken createToken(ServletRequest request,
			ServletResponse response) throws Exception {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		AttributePrincipal principal = (AttributePrincipal) httpRequest.getUserPrincipal();
		String userIdStr = CasUtil.getStringAttribute(principal,"userId");
		Long userId = Long.parseLong(userIdStr);
		if(userId != null)
			return new UMSCasToken(userId, principal);
		else
			return null;
	}
}
