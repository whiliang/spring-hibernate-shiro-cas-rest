package com.ums.umsAdmin.sys.dao;

import java.util.List;

import com.ums.umsAdmin.common.dao.BaseDao;
import com.ums.umsAdmin.sys.form.QueryUserForm;
import com.ums.umsAdmin.sys.model.UMSUser;

public interface UMSUserDao extends BaseDao<UMSUser> {
	public UMSUser findByName(String name);
	public UMSUser findByUUID(String UUID);
	public boolean isExisted(String name);
	public boolean isExisted(Long userId);
	public List<UMSUser> pageQuery(int start, int pageSize, QueryUserForm form);
	public long queryCount(QueryUserForm form);
	public List<UMSUser> blurSearch(String hints);
}
