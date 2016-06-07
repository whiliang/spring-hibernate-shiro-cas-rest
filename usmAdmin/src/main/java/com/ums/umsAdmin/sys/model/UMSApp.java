package com.ums.umsAdmin.sys.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "TAPP")
@JsonIgnoreProperties(value = {"roles"})
public class UMSApp implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8338814377019115556L;
	
	@Id
	@Column(name = "APP_ID")
	private Long appId;
	
	@Column(name = "APP_NAME")
	private String appName;
	
	@OneToMany(mappedBy = "umsApp", fetch = FetchType.LAZY)
	private List<UMSRole> roles;
	
	@OneToMany(mappedBy = "umsApp", fetch = FetchType.LAZY)
	private List<UMSPermission> permissions;

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public List<UMSRole> getRoles() {
		return roles;
	}

	public void setRoles(List<UMSRole> roles) {
		this.roles = roles;
	}

	public List<UMSPermission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<UMSPermission> permissions) {
		this.permissions = permissions;
	}

}
