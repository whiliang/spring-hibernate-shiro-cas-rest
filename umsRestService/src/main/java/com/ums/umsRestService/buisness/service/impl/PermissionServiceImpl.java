package com.ums.umsRestService.buisness.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ums.umsRestService.buisness.dao.UMSPermissionDao;
import com.ums.umsRestService.buisness.dao.UMSRoleDao;
import com.ums.umsRestService.buisness.dao.UMSUserDao;
import com.ums.umsRestService.buisness.model.UMSRole;
import com.ums.umsRestService.buisness.model.UMSUser;
import com.ums.umsRestService.buisness.service.PermissionService;

@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {
	@Autowired
	@Qualifier("umsUserDao")
	private UMSUserDao umsUserDao;

	@Autowired
	@Qualifier("umsPermissionDao")
	private UMSPermissionDao umsPermissionDao;

	@Autowired
	@Qualifier("umsRoleDao")
	private UMSRoleDao umsRoleDao;

	@Override
	public List<String> getPermissionNames(String userName, Long appId) {
		List<String> permissionNames = new ArrayList<String>();
		UMSUser user = umsUserDao.findByUserName(userName);
		List<UMSRole> roles = user.getRoleList();
		for (UMSRole r : roles) {
			UMSRole role = umsRoleDao.findByRoleName(r.getRoleName(), appId);
			if (role != null) {
				for (String p : role.getPermissionsName()) {
					if (!permissionNames.contains(p))
						permissionNames.add(p);
				}
			}
		}
		return permissionNames;
	}

}
