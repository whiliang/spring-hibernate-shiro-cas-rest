package com.ums.umsAdmin.sys.util;

import java.io.Serializable;
import java.util.List;

public class PageModel<M> implements Serializable {

	private static final long serialVersionUID = 8017738615205913351L;
	
	private long totalCount;
	private int pageNum;
	private int pageSize;
	private List<M> items;
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public List<M> getItems() {
		return items;
	}
	public void setItems(List<M> items) {
		this.items = items;
	}
	
	public PageModel(long totalCount, int pageNum, int pageSize, List<M> items) {
		this.totalCount = totalCount;
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.items = items;
	}
	
	
}
