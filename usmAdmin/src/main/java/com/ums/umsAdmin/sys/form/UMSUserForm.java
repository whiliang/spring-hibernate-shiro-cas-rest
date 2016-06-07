package com.ums.umsAdmin.sys.form;

import java.util.List;

public class UMSUserForm {
	private Long userId;
	private String userName;
	private String password;
	private String email;
	private String setPwdByEmail;
	private List<Long> assignedRoles;
	private String oldPassword;
	private String UUID;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSetPwdByEmail() {
		return setPwdByEmail;
	}
	public void setSetPwdByEmail(String setPwdByEmail) {
		this.setPwdByEmail = setPwdByEmail;
	}
	public List<Long> getAssignedRoles() {
		return assignedRoles;
	}
	public void setAssignedRoles(List<Long> assignedRoles) {
		this.assignedRoles = assignedRoles;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getUUID() {
		return UUID;
	}
	public void setUUID(String uUID) {
		UUID = uUID;
	}
	
	

}
