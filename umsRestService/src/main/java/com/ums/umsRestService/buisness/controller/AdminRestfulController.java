package com.ums.umsRestService.buisness.controller;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.ums.umsRestService.buisness.service.PermissionService;
import com.ums.umsRestService.common.exception.BusinessException;

@RestController
public class AdminRestfulController {
	@Autowired
	@Qualifier("permissionService")
	private PermissionService permissionService;

	@RequestMapping("/umsAdmin/getRestfulPermissions/{userName}/{appId}")
	public ArrayList<String> getPermissionNames(@PathVariable String userName,
			@PathVariable Long appId) throws JsonMappingException, IOException {
		if (userName == null || "".equals(userName)) {
			throw new BusinessException("userName.invalid");
		}
		if(appId == null || "".equals(appId)){
			throw new BusinessException("app.invalid");
		}
		ArrayList<String> list = new ArrayList<String>();
		list = (ArrayList<String>) permissionService.getPermissionNames(
				userName, appId);
		return list;
	}
}
