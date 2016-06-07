package com.ums.umsAdmin.sys.dao;

import java.util.List;

import com.ums.umsAdmin.common.dao.BaseDao;
import com.ums.umsAdmin.sys.form.QueryRoleForm;
import com.ums.umsAdmin.sys.model.UMSRole;

public interface UMSRoleDao extends BaseDao<UMSRole> {
	public long queryCount(QueryRoleForm form);
	public List<UMSRole> pageQuery(int start, int pageSize, QueryRoleForm form);
	public List<UMSRole> blurSearch(String hints);
	public UMSRole findByName(String name);
	public boolean isExisted(Long roleId);
	public boolean checkRoleName(String roleName,Long appId );
	public List<UMSRole>findAllNoDefault();
}
