package com.ums.umsRestService.buisness.dao.impl;

import org.springframework.stereotype.Repository;

import com.ums.umsRestService.buisness.dao.UMSRoleDao;
import com.ums.umsRestService.buisness.model.UMSRole;
import com.ums.umsRestService.common.dao.impl.BaseDaoHibernateImpl;


@Repository("umsRoleDao")
public class UMSRoleDaoHibernateImpl extends BaseDaoHibernateImpl<UMSRole> implements
		UMSRoleDao {

	@Override
	public UMSRole findByRoleName(String roleName,Long appId) {
		String hql = "from UMSRole r where r.roleName='" + roleName + "' and r.appId="+appId;
		return (UMSRole) getSession().createQuery(hql).uniqueResult();
	}

}
