package com.ums.umsAdmin.sys.dao.impl;

import org.springframework.stereotype.Repository;

import com.ums.umsAdmin.common.dao.impl.BaseDaoHibernateImpl;
import com.ums.umsAdmin.sys.dao.UMSAppDao;
import com.ums.umsAdmin.sys.model.UMSApp;

@Repository("umsAppDao")
public class UMSAppDaoHibernateImpl extends BaseDaoHibernateImpl<UMSApp>
		implements UMSAppDao {

}
