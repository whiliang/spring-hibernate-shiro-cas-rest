package com.ums.umsAdmin.sys.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jasig.cas.client.util.AssertionHolder;
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
import com.ums.umsAdmin.common.util.ConfigFileUtil;
import com.ums.umsAdmin.sys.form.QueryUserForm;
import com.ums.umsAdmin.sys.form.UMSUserForm;
import com.ums.umsAdmin.sys.model.UMSRole;
import com.ums.umsAdmin.sys.model.UMSUser;
import com.ums.umsAdmin.sys.service.UMSRoleService;
import com.ums.umsAdmin.sys.service.UMSUserService;
import com.ums.umsAdmin.sys.util.PageModel;
import com.ums.umsAdmin.sys.vo.UMSRoleVo;
import com.ums.umsAdmin.sys.vo.UMSUserVo;

@Controller
public class UserController extends BaseController {
	private static final char IS_DEFAULT = 'Y';
	private static final char IS_SYS='1';
	@Autowired
	@Qualifier("umsUserService")
	private UMSUserService userService;

	@Autowired
	@Qualifier("umsRoleService")
	private UMSRoleService roleService;

	@RequestMapping("/user/list")
	public ModelAndView list() {
		ModelAndView mv = new ModelAndView("/user/listUser");
		String userName = AssertionHolder.getAssertion().getPrincipal()
				.getName();
		mv.addObject("userName", userName);
		return mv;
	}

	@RequiresPermissions(value = { "user:query" })
	@RequestMapping("/user/listUsers")
	public @ResponseBody PageModel<UMSUserVo> ajaxListUsers(
			@ModelAttribute("form") QueryUserForm queryUserForm) {
		PageModel<UMSUserVo> pageModel = userService
				.pageQueryOfVo(queryUserForm);
		return pageModel;
	}

	@RequiresPermissions(value = { "user:query" })
	@RequestMapping("/user/getSearchHints")
	public @ResponseBody Set<String> ajaxSearchHints(HttpServletRequest request) {
		String input = request.getParameter("term");
		return userService.searchUserHints(input);
	}

	@RequiresPermissions(value = { "user:delete" })
	@RequestMapping("/user/delete")
	@SystemLog(module = "UserMgt", action = "Delete user.")
	public @ResponseBody Map deleteUser(@RequestParam("userId") Long userId) {
		if (userService.isExisted(userId) == false) {
			throw new BusinessException("userName.inexistent");
		}
		UMSUser user = userService.findUserById(userId);
		if(user.getIsSys()==IS_SYS){
			throw new BusinessException("user.delete.fail");
		}
		userService.deleteUser(userId);
		Map result = operateResult(
				"success",
				getMessage("user.delete.success",
						new Object[] { user.getUserName() }));
		return result;
	}

	@RequiresPermissions(value = { "user:add" })
	@RequestMapping("/user/toAdd")
	public ModelAndView toAddUser() {
		ModelAndView mv = new ModelAndView("/user/addUser");
		List<UMSRoleVo> vos = new ArrayList<UMSRoleVo>();
		List<UMSRole> roleList = roleService.findAllNoDefault();
		for (UMSRole r : roleList) {
			UMSRoleVo v = new UMSRoleVo();
			v.setRoleId(r.getRoleId());
			v.setRoleNameWithApp(r.getUmsApp().getAppName() + "/"
					+ r.getRoleName());
			vos.add(v);
		}
		mv.addObject("roleVos", vos);
		return mv;
	}

	@RequiresPermissions(value = { "user:add" })
	@RequestMapping("/user/addUser")
	@SystemLog(module = "UserMgt", action = "Add user.")
	public @ResponseBody Map addUser(
			@ModelAttribute("user") UMSUserForm userForm) {
		String userName = "";
		if (userForm.getUserName() == null || "".equals(userForm.getUserName())) {
			throw new BusinessException("userName.invalid");
		} else {
			userName = userForm.getUserName();
		}
		if (userService.isExisted(userName)) {
			throw new BusinessException("user.duplicated");
		}
		userService.addUser(userForm);
		Map result = operateResult("success",
				getMessage("user.add.success", new Object[] { userName }));
		return result;
	}

	@RequiresPermissions(value = { "user:edit" })
	@RequestMapping("/user/toUpdate/{userId}")
	public ModelAndView toUpdateUser(@PathVariable Long userId)
			throws JsonProcessingException {
		ModelAndView mv = new ModelAndView("/user/updateUser");
		UMSUser user = userService.findUserById(userId);
		if (userService.isExisted(userId) == false) {
			throw new BusinessException("userName.inexistent");
		}
		if(user.getIsSys()==IS_SYS){
			throw new BusinessException("user.edit.fail");
		}
		List<UMSRoleVo> vos = new ArrayList<UMSRoleVo>();
		List<UMSRole> roleList = roleService.findAllNoDefault();
		List<UMSRole> userRoles = user.getRoleList();
		for (UMSRole r : roleList) {
			UMSRoleVo v = new UMSRoleVo();
			v.setRoleId(r.getRoleId());
			v.setRoleNameWithApp(r.getUmsApp().getAppName() + "/"
					+ r.getRoleName());
			for (UMSRole ur : userRoles) {
				if (ur.getRoleId() == r.getRoleId()) {
					v.setActive(true);
				}
			}
			vos.add(v);
		}
		mv.addObject("roleVos", vos);
		mv.addObject("user", user);
		return mv;
	}

	@RequiresPermissions(value = { "user:edit" })
	@RequestMapping("/user/updateUser")
	public @ResponseBody Map updateUser(
			@ModelAttribute("user") UMSUserForm userForm) {
		UMSUser user = userService.findUserById(userForm.getUserId());
		if (userService.isExisted(userForm.getUserId()) == false) {
			throw new BusinessException("userName.inexistent");
		}
		userService.updateUser(userForm);
		Map result = operateResult("success",
				getMessage("user.update.success", new Object[] {

				userForm.getUserName() }));
		return result;
	}

	@RequiresPermissions(value = { "user:editOwn" })
	@RequestMapping("/user/toUpdateOwn")
	public ModelAndView toUpdateOwn() throws JsonProcessingException {
		ModelAndView mv = new ModelAndView("/user/updateOwn");
		long userId = getLoginUserId();
		UMSUser user = userService.findUserById(userId);
		if (userService.isExisted(userId) == false) {
			throw new BusinessException("userName.inexistent");
		}
		if(user.getIsSys()==IS_SYS){
			throw new BusinessException("user.edit.fail");
		}
		mv.addObject("user", user);
		return mv;
	}

	@RequiresPermissions(value = { "user:editOwn" })
	@RequestMapping("/user/updateOwn")
	public @ResponseBody Map updateOwn(
			@ModelAttribute("user") UMSUserForm userForm) {
		UMSUser user = userService.findUserById(userForm.getUserId());
		if (userService.isExisted(userForm.getUserId()) == false) {
			throw new BusinessException("userName.inexistent");
		}
		userService.updateUser(userForm);
		Map result = operateResult("success",
				getMessage("user.update.success", new Object[] {

				userForm.getUserName() }));
		return result;
	}

	@RequiresPermissions(value = { "user:view" })
	@RequestMapping("/user/viewUser/{userId}")
	public ModelAndView view(@PathVariable Long userId)
			throws JsonProcessingException {
		ModelAndView mv = new ModelAndView("/user/viewUser");
		UMSUser user = userService.findUserById(userId);
		if (userService.isExisted(userId) == false) {
			throw new BusinessException("userName.inexistent");
		}
		UMSUserVo vos = new UMSUserVo();
		String userName = user.getUserName();
		String email = user.getEmail();
		Date loginTime = user.getLoginTime();
		char isSys = user.getIsSys();
		List<UMSRole> roleList = user.getRoleList();
		List<String> list = new ArrayList<String>();
		for (UMSRole r : roleList) {
			if (r.getIsDefault() != IS_DEFAULT) {
				String roleNameWithApp = r.getUmsApp().getAppName() + "/"
						+ r.getRoleName();
				list.add(roleNameWithApp);
			}
		}
		vos.setEmail(email);
		vos.setLoginTime(loginTime);
		vos.setRoleNameWithApp(list);
		vos.setUserId(userId);
		vos.setUserName(userName);
		vos.setIsSys(isSys);
		mv.addObject("user", vos);
		return mv;
	}

	@RequiresPermissions(value = { "user:viewOwn" })
	@RequestMapping("/user/viewOwn")
	public ModelAndView viewOwn() throws JsonProcessingException {
		long userId = getLoginUserId();
		UMSUser user = userService.findUserById(userId);
		ModelAndView mv = new ModelAndView("/user/viewOwn");
		if (userService.isExisted(userId) == false) {
			throw new BusinessException("userName.inexistent");
		}
		UMSUserVo vos = new UMSUserVo();
		String userName = user.getUserName();
		String email = user.getEmail();
		char isSys = user.getIsSys();
		vos.setEmail(email);
		vos.setUserId(userId);
		vos.setUserName(userName);
		vos.setIsSys(isSys);
		mv.addObject("user", vos);
		return mv;
	}

	@RequiresPermissions(value = { "user:changeUserPassword" })
	@RequestMapping("/user/toChangeUserPwd/{userId}")
	public ModelAndView toChangeUserPwd(@PathVariable Long userId)
			throws JsonProcessingException {
		UMSUser user = userService.findUserById(userId);
		if (userService.isExisted(userId) == false) {
			throw new BusinessException("userName.inexistent");
		}
		ModelAndView mv = new ModelAndView("/user/changeUserPwd");
		mv.addObject("loginId", getLoginUserId());
		mv.addObject("user", user);
		return mv;
	}

	@RequiresPermissions(value = { "user:changeUserPassword" })
	@RequestMapping("/user/changeUserPwd")
	public @ResponseBody Map changeUserPwd(
			@ModelAttribute("user") UMSUserForm userForm) {
		userService.changeUserPwd(userForm);
		Map result = operateResult(
				"success",
				getMessage("user.changePwd.success",
						new Object[] { userForm.getUserName() }));
		return result;
	}

	@RequiresPermissions(value = { "user:changeOwnPassword" })
	@RequestMapping("/user/toChangeOwnPwd")
	public ModelAndView toChangeOwnPwd() throws JsonProcessingException {
		long userId = getLoginUserId();
		UMSUser user = userService.findUserById(userId);
		if (userService.isExisted(userId) == false) {
			throw new BusinessException("userName.inexistent");
		}
		if(user.getIsSys()==IS_SYS){
			throw new BusinessException("user.edit.fail");
		}
		ModelAndView mv = new ModelAndView("/user/changeOwnPwd");
		mv.addObject("user", user);
		return mv;
	}

	@RequiresPermissions(value = { "user:changeOwnPassword" })
	@RequestMapping("/user/changeOwnPwd")
	public @ResponseBody Map changeOwnPwd(
			@ModelAttribute("user") UMSUserForm userForm) {
		UMSUser user = userService.findUserById(userForm.getUserId());
		if (userService.isExisted(userForm.getUserId()) == false) {
			throw new BusinessException("userName.inexistent");
		}
		String oldPwd = userForm.getOldPassword();
		String oldPwdDiges = DigestUtils.sha256Hex(oldPwd);
		if (!oldPwdDiges.equals(user.getPassword())) {
			throw new BusinessException("user.changeOwnPwd.fail");
		}
		userService.changeUserPwd(userForm);
		Map result = operateResult("success",
				getMessage("user.changeOwnPwd.success", null));
		return result;
	}

	@RequestMapping("/public/toSetPassword/{UUID}")
	public ModelAndView toSetPassword(@PathVariable String UUID)
			throws JsonProcessingException {
		UMSUser user = userService.findUserByUUID(UUID);
		String url = ConfigFileUtil
				.getValue("config.properties", "umsAdminUrl");
		if (user == null) {
			ModelAndView mv = new ModelAndView("/public/expire");
			return mv;
		} else {
			ModelAndView mv = new ModelAndView("/public/setPassword");
			mv.addObject("user", user);
			mv.addObject("url", url);
			return mv;
		}
	}

	@RequestMapping("/public/setPwd")
	public @ResponseBody Map setPws(@ModelAttribute("user") UMSUserForm userForm) {
		if (userService.isExisted(userForm.getUserId()) == false) {
			throw new BusinessException("userName.inexistent");
		}
		userService.setPwd(userForm);
		Map result = operateResult("success",
				getMessage("user.setPwd.success", null));

		return result;
	}

	@RequestMapping("/public/toForgetMyPwd")
	public ModelAndView toInputUserName() throws JsonProcessingException {
		ModelAndView mv = new ModelAndView("/public/forgetPwd");
		return mv;
	}

	@RequestMapping("/public/forgetMyPwd")
	public @ResponseBody Map forgetPwd(
			@ModelAttribute("user") UMSUserForm userForm) {
		if (userService.findUserByName(userForm.getUserName()) == null) {
			throw new BusinessException("userName.inexistent");
		}
		if(userService.findUserByName(userForm.getUserName()).getIsSys()==IS_SYS){
			throw new BusinessException("system.user.cannot");
		}
		userService.forgetPwd(userForm);
		Map result = operateResult("success",
				getMessage("email.send.success", null));

		return result;
	}

}
