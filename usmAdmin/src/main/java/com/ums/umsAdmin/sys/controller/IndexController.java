package com.ums.umsAdmin.sys.controller;

import org.apache.shiro.SecurityUtils;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.util.AssertionHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ums.umsAdmin.common.security.CasUtil;
import com.ums.umsAdmin.common.security.UMSCasToken;
import com.ums.umsAdmin.sys.service.UMSUserService;

@Controller(value = "indexController")
public class IndexController {
	
	@Autowired
	@Qualifier("umsUserService")
	private UMSUserService userService;
	
	@RequestMapping("/")
	public ModelAndView hello(Model model, RedirectAttributes attr){
		ModelAndView mv = new ModelAndView("redirect:/user/list");
		if(SecurityUtils.getSubject().isAuthenticated()){
			return mv;
		}else{
			AttributePrincipal principal = AssertionHolder.getAssertion().getPrincipal();
			String userIdStr = CasUtil.getStringAttribute(principal,"userId");
			Long userId = Long.parseLong(userIdStr);
			SecurityUtils.getSubject().login(new UMSCasToken(userId,principal));
			userService.updateLoginTime(userId);
			return mv;
		}
		
	}
	
	@RequestMapping("/public/setPassword")
	public String changePassword(){
		return "/public/setPassword";
	}
	
	@RequestMapping("/public/success")
	public String sendEmailSuccess(){
		return "/public/success";
	}
}
