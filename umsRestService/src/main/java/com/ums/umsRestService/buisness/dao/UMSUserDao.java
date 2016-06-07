package com.ums.umsRestService.buisness.dao;

import com.ums.umsRestService.buisness.model.UMSUser;
import com.ums.umsRestService.common.dao.BaseDao;

public interface UMSUserDao extends BaseDao<UMSUser> {
	public UMSUser findByUserName(String userName);

}
