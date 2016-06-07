package com.ums.umsRestService.buisness.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.ums.umsRestService.buisness.dao.UMSPermissionDao;
import com.ums.umsRestService.buisness.model.UMSPermission;
import com.ums.umsRestService.common.dao.impl.BaseDaoHibernateImpl;


@Repository("umsPermissionDao")
public class UMSPermissionDaoHibernateImpl extends BaseDaoHibernateImpl<UMSPermission>
		implements UMSPermissionDao {

}
