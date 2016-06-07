package com.ums.umsRestService.common.dataSource;

import org.aspectj.lang.JoinPoint;

public class DataSourceInterceptor {
	public void setdataSourceAdmin(JoinPoint jp) {
		DatabaseContextHolder.setCustomerType("adminDataSource");
	}
	
	public void setdataSourceH5(JoinPoint jp) {
		DatabaseContextHolder.setCustomerType("h5DataSource");
	}
}
