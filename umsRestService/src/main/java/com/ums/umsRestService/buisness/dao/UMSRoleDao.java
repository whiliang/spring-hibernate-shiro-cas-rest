package com.ums.umsRestService.buisness.dao;

import com.ums.umsRestService.buisness.model.UMSRole;
import com.ums.umsRestService.common.dao.BaseDao;

public interface UMSRoleDao extends BaseDao<UMSRole> {
	public UMSRole findByRoleName(String roleName,Long appId);
}
