package com.ums.umsAdmin.sys.util;

public class PageUtil {
	public static int getPageSize(long totalCount, int pageNumber, int pageSize){
		int start = (pageNumber - 1) * pageSize;
		if(start >= totalCount){
			start = 0;
		}
		return start;
	}
}
