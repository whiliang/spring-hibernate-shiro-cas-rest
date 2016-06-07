package com.ums.umsAdmin.sys.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "TUSER")
@JsonIgnoreProperties(value = {"roleList"})
public class UMSUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6095714172184673974L;

	@Id
	@GeneratedValue(generator = "USER_ID_GEN")
	@GenericGenerator(name = "USER_ID_GEN", strategy = "enhanced-table", parameters = {
			@Parameter(name = "table_name", value = "TSEQUENCE"),
			@Parameter(name = "value_column_name", value = "NEXT_VALUE"),
			@Parameter(name = "segment_column_name", value = "SEQ_NAME"),
			@Parameter(name = "segment_value", value = "USER_ID"),
			@Parameter(name = "increment_size", value = "1"),
			@Parameter(name = "optimizer", value = "pooled-lo") })
	@Column(name = "USER_ID")
	private Long userId;

	@Column(name = "USER_NAME")
	private String userName;

	@Column(name = "USER_PASSWORD")
	private String password;

	@Column(name = "USER_EMAIL")
	private String email;

	@Column(name = "USER_LOGINTIME")
	private Date loginTime;
	
	@Column(name = "USER_RESTPASSUUID")
	private String resetPassUUID;
	
	@Column(name = "USER_ISSYS")
	private char isSys;

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinTable(name = "TUSER_ROLE", 
	joinColumns = { @JoinColumn(name = "USER_ID") }, 
	inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
	private List<UMSRole> roleList;

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

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public List<UMSRole> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<UMSRole> roleList) {
		this.roleList = roleList;
	}

	public String getResetPassUUID() {
		return resetPassUUID;
	}

	public void setResetPassUUID(String resetPassUUID) {
		this.resetPassUUID = resetPassUUID;
	}
	

	public char getIsSys() {
		return isSys;
	}

	public void setIsSys(char isSys) {
		this.isSys = isSys;
	}

	@Transient
	public Set<String> getRolesName() {
		List<UMSRole> roles = getRoleList();
		Set<String> set = new HashSet<String>();
		for (UMSRole role : roles) {
			set.add(role.getRoleName());
		}
		return set;
	}
}
