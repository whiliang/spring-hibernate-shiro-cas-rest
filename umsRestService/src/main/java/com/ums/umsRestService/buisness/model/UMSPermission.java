package com.ums.umsRestService.buisness.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TPERMISSION")
public class UMSPermission implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9039577606857902686L;

	@Id
	@Column(name = "PERMISSION_ID")
	private Long permissionId;
	
	@Column(name = "PERMISSION_NAME")
	private String permissionName;
	
	@Column(name = "APP_ID")
	private Long appId;
	
	@ManyToMany
	@JoinTable(name = "TROLE_PERMISSION", joinColumns = { @JoinColumn(name = "PERMISSION_ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
	private List<UMSRole> roleList;
	
	public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public List<UMSRole> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<UMSRole> roleList) {
		this.roleList = roleList;
	}

}

