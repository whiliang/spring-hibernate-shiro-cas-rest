package com.ums.umsAdmin.sys.model;

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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "TPERMISSION")
@JsonIgnoreProperties(value = {"roleList"})
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
	
	@Column(name="CN_NAME")
	private String cnName;
	
	@Column(name="IS_DEFAULT")
	private char isDefault;;
	
	@ManyToOne
	@JoinColumn(name = "APP_ID")
	private UMSApp umsApp;
	
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

	public UMSApp getUmsApp() {
		return umsApp;
	}

	public void setUmsApp(UMSApp umsApp) {
		this.umsApp = umsApp;
	}

	public List<UMSRole> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<UMSRole> roleList) {
		this.roleList = roleList;
	}

	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	public char getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(char isDefault) {
		this.isDefault = isDefault;
	}
	
	

}

