package com.ums.umsRestService.buisness.dao.impl;

import org.springframework.stereotype.Repository;

import com.ums.umsRestService.buisness.dao.UMSAppDao;
import com.ums.umsRestService.buisness.model.UMSApp;
import com.ums.umsRestService.common.dao.impl.BaseDaoHibernateImpl;

@Repository("tappDao")
public class UMSAppDaoHibernateImpl extends BaseDaoHibernateImpl<UMSApp> implements
		UMSAppDao {

}
