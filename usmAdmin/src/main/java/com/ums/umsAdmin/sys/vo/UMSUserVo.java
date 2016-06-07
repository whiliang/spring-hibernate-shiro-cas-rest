package com.ums.umsAdmin.sys.vo;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class UMSUserVo {
	private Long userId;
	private String userName;
	private String email;
	private Date loginTime;
	private char isSys;
	public List<String> roleNameWithApp;
	
	public List<String> getRoleNameWithApp() {
		return roleNameWithApp;
	}
	public void setRoleNameWithApp(List<String> roleNameWithApp) {
		this.roleNameWithApp = roleNameWithApp;
	}
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	public char getIsSys() {
		return isSys;
	}
	public void setIsSys(char isSys) {
		this.isSys = isSys;
	}
	
	
}
