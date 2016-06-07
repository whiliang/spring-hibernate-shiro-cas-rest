package com.ums.umsRestService.buisness.dao.impl;

import org.springframework.stereotype.Repository;

import com.ums.umsRestService.buisness.dao.UMSUserDao;
import com.ums.umsRestService.buisness.model.UMSUser;
import com.ums.umsRestService.common.dao.impl.BaseDaoHibernateImpl;
@Repository("umsUserDao")
public class UMSUserDaoHibernateImpl extends BaseDaoHibernateImpl<UMSUser>
		implements UMSUserDao {
	@Override
	public UMSUser findByUserName(String userName){
		String hql = "from UMSUser u where u.userName='" +userName+"'";
		return (UMSUser) getSession().createQuery(hql).uniqueResult();
	}
}
