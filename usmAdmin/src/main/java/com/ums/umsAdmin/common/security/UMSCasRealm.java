package com.ums.umsAdmin.common.security;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import com.ums.umsAdmin.sys.model.UMSRole;
import com.ums.umsAdmin.sys.model.UMSUser;
import com.ums.umsAdmin.sys.service.UMSUserService;

@Transactional
public class UMSCasRealm extends AuthorizingRealm {
	@Autowired
	@Qualifier("umsUserService")
	private UMSUserService userService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		AttributePrincipal principal = (AttributePrincipal) principals.asList()
				.get(0);
		String loginName = principal.getName();
		
		UMSUser user = userService.findUserByName(loginName);
//		List<String> list = new ArrayList<String>();
//		list.add("administrator");
//		List<String> testList;
//		try {
//			testList = RESTClient.getPermissionsName(list);
//			for(String s : testList){
//				System.out.println("########### " + s);
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
			
		if(user != null){
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			info.setRoles(user.getRolesName());
			List<UMSRole> roleList = user.getRoleList();
			for (UMSRole role : roleList) {
				info.addStringPermissions(role.getPermissionsName());
			}
			return info;
		}
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		if (token != null)
			return new SimpleAuthenticationInfo(token.getPrincipal(),
					token.getCredentials(), getName());
		else
			return null;
	} 

	@Override
	public Class getAuthenticationTokenClass() {
		// TODO Auto-generated method stub
		return UMSCasToken.class;
	}

	@Override
	public void setAuthenticationTokenClass(
			Class<? extends AuthenticationToken> authenticationTokenClass) {
		// TODO Auto-generated method stub
		super.setAuthenticationTokenClass(UMSCasToken.class);
	}
	
}
