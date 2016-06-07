package com.ums.umsRestService.buisness.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "TROLE")
public class UMSRole implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1027136954803204065L;

	@Id
	@Column(name = "ROLE_ID")
	private Long roleId;

	@Column(name = "ROLE_NAME")
	private String roleName;

	@Column(name = "APP_ID")
	private Long appId;

	@ManyToMany
	@JoinTable(name = "TROLE_PERMISSION", joinColumns = { @JoinColumn(name = "ROLE_ID") }, inverseJoinColumns = { @JoinColumn(name = "PERMISSION_ID") })
	private List<UMSPermission> permissionList;

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

	public List<UMSPermission> getPermissionList() {
		return permissionList;
	}

	public void setPermissionList(List<UMSPermission> permissionList) {
		this.permissionList = permissionList;
	}
	
	@Transient
	public List<String> getPermissionsName() {
		List<String> list = new ArrayList<String>();
		List<UMSPermission> perlist = getPermissionList();
		for (UMSPermission per : perlist) {
			if (!list.contains(per.getPermissionName()))
				list.add(per.getPermissionName());
		}
		return list;
	}
}
