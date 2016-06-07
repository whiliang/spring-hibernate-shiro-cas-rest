package com.ums.umsAdmin.sys.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "TROLE")
@JsonIgnoreProperties(value = {"userList","permissionList"})
public class UMSRole implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1027136954803204065L;

	@Id
	@GeneratedValue(generator = "ROLE_ID_GEN")
	@GenericGenerator(name = "ROLE_ID_GEN", strategy = "enhanced-table", parameters = {
			@Parameter(name = "table_name", value = "TSEQUENCE"),
			@Parameter(name = "value_column_name", value = "NEXT_VALUE"),
			@Parameter(name = "segment_column_name", value = "SEQ_NAME"),
			@Parameter(name = "segment_value", value = "ROLE_ID"),
			@Parameter(name = "increment_size", value = "1"),
			@Parameter(name = "optimizer", value = "pooled-lo") })
	@Column(name = "ROLE_ID")
	private Long roleId;

	@Column(name = "ROLE_NAME")
	private String roleName;
	
	@Column(name = "IS_DEFAULT")
	private char isDefault = 'N';

	@ManyToOne
	@JoinColumn(name = "APP_ID")
	private UMSApp umsApp;

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinTable(name = "TROLE_PERMISSION", 
	joinColumns = { @JoinColumn(name = "ROLE_ID") }, 
	inverseJoinColumns = { @JoinColumn(name = "PERMISSION_ID") })
	private List<UMSPermission> permissionList;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "roleList")
	private List<UMSUser> userList;

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
	
	
	public char getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(char isDefault) {
		this.isDefault = isDefault;
	}

	public UMSApp getUmsApp() {
		return umsApp;
	}

	public void setUmsApp(UMSApp umsApp) {
		this.umsApp = umsApp;
	}

	public List<UMSPermission> getPermissionList() {
		return permissionList;
	}

	public void setPermissionList(List<UMSPermission> permissionList) {
		this.permissionList = permissionList;
	}

	public List<UMSUser> getUserList() {
		return userList;
	}

	public void setUserList(List<UMSUser> userList) {
		this.userList = userList;
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
