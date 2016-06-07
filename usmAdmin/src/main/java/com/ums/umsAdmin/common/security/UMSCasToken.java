package com.ums.umsAdmin.common.security;

import org.apache.shiro.authc.RememberMeAuthenticationToken;
import org.jasig.cas.client.authentication.AttributePrincipal;

public class UMSCasToken implements RememberMeAuthenticationToken {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1811497806724746757L;
	
	private AttributePrincipal principal;
    private Long userId;
    
    public UMSCasToken(Long userId, AttributePrincipal principal) {
        this.userId = userId;
        this.principal = principal;
    }
	@Override
	public Object getPrincipal() {
		// TODO Auto-generated method stub
		return principal;
	}

	@Override
	public Object getCredentials() {
		// TODO Auto-generated method stub
		return userId;
	}

	@Override
	public boolean isRememberMe() {
		// TODO Auto-generated method stub
		return false;
	}

}
