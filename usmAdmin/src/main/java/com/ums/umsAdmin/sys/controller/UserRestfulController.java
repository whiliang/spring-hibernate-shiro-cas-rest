package com.ums.umsAdmin.sys.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ums.umsAdmin.sys.model.UMSUser;
import com.ums.umsAdmin.sys.service.UMSUserService;
import com.ums.umsAdmin.sys.vo.UMSUserVo;

@RestController
public class UserRestfulController {
	@Autowired
	@Qualifier("umsUserService")
	private UMSUserService userService;
	
	@RequiresPermissions(value = { "user:query" })
	@RequestMapping("/user/getRestfulUsers")
	public List<UMSUserVo> getUsers(){
		List<UMSUser> users = userService.findAll();
		List<UMSUserVo> userVos = new ArrayList<UMSUserVo>();
		for(UMSUser user : users){
			UMSUserVo userVo = new UMSUserVo();
			userVo.setUserName(user.getUserName());
			userVo.setEmail(user.getEmail());
			userVo.setLoginTime(user.getLoginTime());
			userVos.add(userVo);
		}
		return userVos;
	}
}
