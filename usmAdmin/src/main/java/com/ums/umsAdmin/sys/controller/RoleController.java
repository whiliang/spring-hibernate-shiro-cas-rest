package com.ums.umsAdmin.sys.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ums.umsAdmin.common.aop.SystemLog;
import com.ums.umsAdmin.common.controller.BaseController;
import com.ums.umsAdmin.common.exception.BusinessException;
import com.ums.umsAdmin.sys.form.QueryRoleForm;
import com.ums.umsAdmin.sys.form.UMSRoleForm;
import com.ums.umsAdmin.sys.model.UMSApp;
import com.ums.umsAdmin.sys.model.UMSPermission;
import com.ums.umsAdmin.sys.model.UMSRole;
import com.ums.umsAdmin.sys.service.UMSAppService;
import com.ums.umsAdmin.sys.service.UMSRoleService;
import com.ums.umsAdmin.sys.util.PageModel;
import com.ums.umsAdmin.sys.vo.UMSPermissionVo;
import com.ums.umsAdmin.sys.vo.UMSRoleVo;

@Controller
public class RoleController extends BaseController {
	private static final char IS_DEFAULT = 'N';
	private static final Long SYS_ROLE_ID_1=1L;
	private static final Long SYS_ROLE_ID_2=3L;
	@Autowired
	@Qualifier("umsRoleService")
	private UMSRoleService roleService;

	@Autowired
	@Qualifier("umsAppService")
	private UMSAppService appService;

	@RequiresPermissions(value = { "role:query" })
	@RequestMapping("/role/list")
	public @ResponseBody PageModel<UMSRoleVo> ajaxListRoles(
			@ModelAttribute("form") QueryRoleForm queryRoleForm) {
		PageModel<UMSRoleVo> pageModel = roleService
				.pageQueryOfVo(queryRoleForm);
		System.out.println();
		return pageModel;
	}

	@RequiresPermissions(value = { "role:query" })
	@RequestMapping("/role/getSearchHints")
	public @ResponseBody Set<String> ajaxSearchHints(HttpServletRequest request) {
		String input = request.getParameter("term");
		return roleService.searchUserHints(input);
	}

	@RequiresPermissions(value = { "role:add" })
	@RequestMapping("/role/toAdd")
	public ModelAndView toAddRole() {
		ModelAndView mv = new ModelAndView("/role/addRole");
		List<UMSApp> appList = appService.findAll();
		List<UMSRoleVo> vos = new ArrayList<>();
		for (UMSApp a : appList) {
			UMSRoleVo v = new UMSRoleVo();
			v.setAppId(a.getAppId());
			v.setAppName(a.getAppName());
			vos.add(v);
		}
		mv.addObject("appVos", vos);
		return mv;
	}

	@RequiresPermissions(value = { "role:add" })
	@RequestMapping("/role/getPermission")
	public @ResponseBody List<UMSPermissionVo> ajaxGetPermission(
			@RequestParam("appId") Long appId) {
		UMSApp app = appService.findAppById(appId);
		List<UMSPermissionVo> permissionVo = new ArrayList<>();
		List<UMSPermission> permissionList = app.getPermissions();
		for (UMSPermission p : permissionList) {
			if (p.getIsDefault() == IS_DEFAULT) {
				UMSPermissionVo vos = new UMSPermissionVo();
				vos.setPermissionId(p.getPermissionId());
				vos.setCnName(p.getCnName());
				permissionVo.add(vos);
			}
		}
		return permissionVo;
	}

	@RequiresPermissions(value = { "role:add" })
	@RequestMapping("/role/addRole")
	@SystemLog(module = "RoleMgt", action = "Add role.")
	public @ResponseBody Map addRole(
			@ModelAttribute("role") UMSRoleForm roleForm) {
		if (roleService.checkRoleName(roleForm.getRoleName(),
				roleForm.getAppId())) {
			throw new BusinessException("roleName.exist");
		}
		roleService.addRole(roleForm);
		Map result = operateResult(
				"success",
				getMessage("role.add.success",
						new Object[] { roleForm.getRoleName() }));
		return result;
	}

	@RequiresPermissions(value = { "role:delete" })
	@RequestMapping("/role/deleteRole")
	@SystemLog(module = "RoleMgt", action = "Delete role.")
	public @ResponseBody Map deleteUser(@RequestParam("roleId") Long roleId) {
		UMSRole role = roleService.findRoleById(roleId);
		if (roleService.isExisted(roleId) == false) {
			throw new BusinessException("roleName.inexistent");
		}
		if(role.getIsDefault()=='Y'||roleId==SYS_ROLE_ID_1||roleId==SYS_ROLE_ID_2){
			throw new BusinessException("role.edit.fail");
		}
		roleService.deleteRole(roleId);
		Map result = operateResult(
				"success",
				getMessage("role.delete.success",
						new Object[] { role.getRoleName() }));
		return result;
	}

	@RequiresPermissions(value = { "role:view" })
	@RequestMapping("/role/viewRole/{roleId}")
	public ModelAndView view(@PathVariable Long roleId)
			throws JsonProcessingException {
		ModelAndView mv = new ModelAndView("/role/viewRole");
		UMSRole role = roleService.findRoleById(roleId);
		if (roleService.isExisted(roleId) == false) {
			throw new BusinessException("roleName.inexistent");
		}
		UMSRoleVo roleVo = new UMSRoleVo();
		List<String> cnName = new ArrayList<>();
		String roleName = role.getRoleName();
		String appName = role.getUmsApp().getAppName();
		List<UMSPermission> permissions = role.getPermissionList();
		for (UMSPermission p : permissions) {
			if(p.getIsDefault()==IS_DEFAULT){
			cnName.add(p.getCnName());
			}
		}
		roleVo.setAppName(appName);
		roleVo.setPermissionName(cnName);
		roleVo.setRoleId(roleId);
		roleVo.setRoleName(roleName);
		mv.addObject("roleVo", roleVo);
		return mv;
	}

	@RequiresPermissions(value = { "role:edit" })
	@RequestMapping("/role/toUpdate/{roleId}")
	public ModelAndView toUpdateRole(@PathVariable Long roleId)
			throws JsonProcessingException {
		ModelAndView mv = new ModelAndView("/role/updateRole");
		if (roleService.isExisted(roleId) == false) {
			throw new BusinessException("roleName.inexistent");
		}
		UMSRole role = roleService.findRoleById(roleId);
		if(role.getIsDefault()=='Y'||roleId==SYS_ROLE_ID_1||roleId==SYS_ROLE_ID_2){
			throw new BusinessException("role.delete.fail");
		}
		String roleName = role.getRoleName();
		String appName = role.getUmsApp().getAppName();
		UMSRoleVo roleVo = new UMSRoleVo();
		roleVo.setAppName(appName);
		roleVo.setRoleId(roleId);
		roleVo.setRoleName(roleName);
		List<UMSPermission> appPermissions = role.getUmsApp().getPermissions();
		List<UMSPermission> permissions = role.getPermissionList();
		List<UMSPermissionVo> vos = new ArrayList<>();
		for (UMSPermission ap : appPermissions) {
			if (ap.getIsDefault() == IS_DEFAULT) {
				UMSPermissionVo vo = new UMSPermissionVo();
				for (UMSPermission p : permissions) {
					if (ap.getPermissionId() == p.getPermissionId()) {
						vo.setActive(true);
					}
				}
				vo.setCnName(ap.getCnName());
				vo.setPermissionId(ap.getPermissionId());
				vos.add(vo);
			}
		}
		mv.addObject("roleVo", roleVo);
		mv.addObject("permissionVo", vos);
		return mv;
	}

	@RequiresPermissions(value = { "role:edit" })
	@RequestMapping("/role/updateRole")
	public @ResponseBody Map updateRole(
			@ModelAttribute("role") UMSRoleForm roleForm) {
		if (roleService.isExisted(roleForm.getRoleId()) == false) {
			throw new BusinessException("roleName.inexistent");
		}
		roleService.updateRole(roleForm);
		Map result = operateResult("success",
				getMessage("role.update.success", new Object[] {

				roleForm.getRoleName() }));
		return result;
	}
}
