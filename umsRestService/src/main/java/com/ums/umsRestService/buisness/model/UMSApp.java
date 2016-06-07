package com.ums.umsRestService.buisness.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TAPP")
public class UMSApp implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2896655449073830872L;
	@Id
	@Column(name = "FId")
	private Long appId;
	
	@Column(name = "FName")
	private String appName;

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
}
