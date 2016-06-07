package com.ums.umsAdmin.sys.vo;

import java.util.List;

public class UMSRoleVo {
	public Long roleId;
	public String roleNameWithApp;
	public String roleName;
	public String appName;
	public List<String>permissionName;
	public boolean active;
	public Long appId;
	
	
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public String getRoleNameWithApp() {
		return roleNameWithApp;
	}
	public void setRoleNameWithApp(String roleNameWithApp) {
		this.roleNameWithApp = roleNameWithApp;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public Long getAppId() {
		return appId;
	}
	public void setAppId(Long appId) {
		this.appId = appId;
	}
	public List<String> getPermissionName() {
		return permissionName;
	}
	public void setPermissionName(List<String> permissionName) {
		this.permissionName = permissionName;
	}
	
	
	
	
	
}
