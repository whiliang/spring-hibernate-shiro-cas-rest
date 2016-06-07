package com.ums.umsAdmin.sys.form;

import java.io.Serializable;

public class FilterForm implements Serializable{
	private static final long serialVersionUID = 3645801774068382275L;
	private int pageNum;
	private int numPerPage;
	private String orderField;
	private String orderDirection;
	private static final int DEFAULT_PAGE_SIZE = 10;
	
	public int getPageNum() {
		if(pageNum <= 0){
			pageNum = 1;
		}
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getNumPerPage() {
		if(numPerPage <= 0){
			numPerPage = DEFAULT_PAGE_SIZE;
		}
		return numPerPage;
	}
	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}
	public String getOrderField() {
		return orderField;
	}
	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}
	public String getOrderDirection() {
		return orderDirection;
	}
	public void setOrderDirection(String orderDirection) {
		this.orderDirection = orderDirection;
	}
}
