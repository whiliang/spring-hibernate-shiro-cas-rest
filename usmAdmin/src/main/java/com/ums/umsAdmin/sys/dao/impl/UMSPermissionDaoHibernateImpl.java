package com.ums.umsAdmin.sys.dao.impl;

import org.springframework.stereotype.Repository;

import com.ums.umsAdmin.common.dao.impl.BaseDaoHibernateImpl;
import com.ums.umsAdmin.sys.dao.UMSPermissionDao;
import com.ums.umsAdmin.sys.model.UMSPermission;

@Repository("umsPermissionDao")
public class UMSPermissionDaoHibernateImpl extends BaseDaoHibernateImpl<UMSPermission>
		implements UMSPermissionDao {

}
