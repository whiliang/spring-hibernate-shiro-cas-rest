package com.ums.umsAdmin.sys.form;

import java.util.List;

public class UMSRoleForm {
	private Long roleId;
	private String roleName;
	private Long appId;
	private List<Long> assignedPermissions;
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Long getAppId() {
		return appId;
	}
	public void setAppId(Long appId) {
		this.appId = appId;
	}
	public List<Long> getAssignedPermissions() {
		return assignedPermissions;
	}
	public void setAssignedPermissions(List<Long> assignedPermissions) {
		this.assignedPermissions = assignedPermissions;
	}
	
}
