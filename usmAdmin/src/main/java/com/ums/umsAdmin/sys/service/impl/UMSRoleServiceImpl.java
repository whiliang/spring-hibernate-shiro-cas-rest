package com.ums.umsAdmin.sys.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ums.umsAdmin.sys.dao.UMSAppDao;
import com.ums.umsAdmin.sys.dao.UMSPermissionDao;
import com.ums.umsAdmin.sys.dao.UMSRoleDao;
import com.ums.umsAdmin.sys.form.QueryRoleForm;
import com.ums.umsAdmin.sys.form.UMSRoleForm;
import com.ums.umsAdmin.sys.model.UMSApp;
import com.ums.umsAdmin.sys.model.UMSPermission;
import com.ums.umsAdmin.sys.model.UMSRole;
import com.ums.umsAdmin.sys.service.UMSRoleService;
import com.ums.umsAdmin.sys.util.PageModel;
import com.ums.umsAdmin.sys.util.PageUtil;
import com.ums.umsAdmin.sys.vo.UMSRoleVo;

@Service("umsRoleService")
public class UMSRoleServiceImpl implements UMSRoleService {
	@Autowired
	@Qualifier("umsRoleDao")
	private UMSRoleDao umsRoleDao;
	@Autowired
	@Qualifier("umsPermissionDao")
	private UMSPermissionDao umsPermissionDao;

	@Autowired
	@Qualifier("umsAppDao")
	private UMSAppDao umsAppDao;

	@Override
	public List<UMSRole> findAllNoDefault() {
		// TODO Auto-generated method stub
		return umsRoleDao.findAllNoDefault();
	}

	public PageModel<UMSRoleVo> pageQueryOfVo(QueryRoleForm queryRoleForm) {
		long totalCount = umsRoleDao.queryCount(queryRoleForm);
		int pageNum = queryRoleForm.getPageNum();
		int pageSize = queryRoleForm.getNumPerPage();
		int pageStart = PageUtil.getPageSize(totalCount, pageNum, pageSize);
		List<UMSRole> umsRoles = umsRoleDao.pageQuery(pageStart, pageSize,
				queryRoleForm);
		List<UMSRoleVo> roleVos = convertoVo(umsRoles);
		PageModel<UMSRoleVo> pageModel = new PageModel<UMSRoleVo>(totalCount,
				pageNum, pageSize, roleVos);
		return pageModel;
	}

	private List<UMSRoleVo> convertoVo(List<UMSRole> roles) {
		List<UMSRoleVo> vos = new ArrayList<UMSRoleVo>();
		for (UMSRole role : roles) {
				UMSRoleVo vo = new UMSRoleVo();
				vo.setRoleId(role.getRoleId());
				vo.setRoleName(role.getRoleName());
				vo.setAppName(role.getUmsApp().getAppName());
				vos.add(vo);
		}
		return vos;
	}

	public Set<String> searchUserHints(String input) {
		Set<String> set = new HashSet<String>();
		List<UMSRole> roles = umsRoleDao.blurSearch(input);
		if (roles.size() > 0) {
			for (UMSRole role : roles) {
					if (role.getRoleName().contains(input)) {
						set.add(role.getRoleName());
					} else if (role.getUmsApp().getAppName().contains(input)) {
						set.add(role.getUmsApp().getAppName());
					}
			}
		}
		return set;
	}

	public Long addRole(UMSRoleForm roleForm) {
		UMSRole role = new UMSRole();
		UMSApp app = umsAppDao.getById(roleForm.getAppId());
		List<Long> permissionIds = roleForm.getAssignedPermissions();
		List<UMSPermission> permissions = new ArrayList<>();
		for (Long id : permissionIds) {
			UMSPermission permission = new UMSPermission();
			permission = umsPermissionDao.getById(id);
			permissions.add(permission);
		}
		role.setPermissionList(permissions);
		role.setRoleName(roleForm.getRoleName());
		role.setUmsApp(app);
		
		umsRoleDao.save(role);
		return role.getRoleId();
	}

	public UMSRole findRoleById(Long id) {
		return umsRoleDao.getById(id);
	}

	public UMSRole findRoleByName(String name) {
		return umsRoleDao.findByName(name);
	}

	public void deleteRole(Long id) {
		umsRoleDao.delete(id);
	}

	public boolean isExisted(Long roleId) {
		return umsRoleDao.isExisted(roleId);
	}

	public boolean checkRoleName(String roleName, Long appId) {
		return umsRoleDao.checkRoleName(roleName, appId);
	}

	public void updateRole(UMSRoleForm roleForm) {
		UMSRole role = umsRoleDao.getById(roleForm.getRoleId());
		List<Long> permissionIds = roleForm.getAssignedPermissions();
		List<UMSPermission> permissionList = new ArrayList<>();
		for (Long p : permissionIds) {
			UMSPermission permission = new UMSPermission();
			permission = umsPermissionDao.getById(p);
			permissionList.add(permission);
		}
		role.setPermissionList(permissionList);
		umsRoleDao.update(role);
	}

}
