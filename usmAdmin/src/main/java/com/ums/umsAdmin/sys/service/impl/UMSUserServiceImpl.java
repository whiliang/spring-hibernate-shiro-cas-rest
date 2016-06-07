package com.ums.umsAdmin.sys.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ums.umsAdmin.common.aop.SystemLog;
import com.ums.umsAdmin.sys.dao.UMSRoleDao;
import com.ums.umsAdmin.sys.dao.UMSUserDao;
import com.ums.umsAdmin.sys.form.QueryUserForm;
import com.ums.umsAdmin.sys.form.UMSUserForm;
import com.ums.umsAdmin.sys.model.UMSRole;
import com.ums.umsAdmin.sys.model.UMSUser;
import com.ums.umsAdmin.sys.service.UMSUserService;
import com.ums.umsAdmin.sys.util.ConstantsUtil;
import com.ums.umsAdmin.sys.util.PageModel;
import com.ums.umsAdmin.sys.util.PageUtil;
import com.ums.umsAdmin.sys.util.UTCTimeUtil;
import com.ums.umsAdmin.sys.vo.UMSUserVo;

@Service("umsUserService")
public class UMSUserServiceImpl implements UMSUserService {
	private static final Long DEFAULT_ROLE_ID=2L;
	@Autowired
	@Qualifier("umsUserDao")
	private UMSUserDao umsUserDao;
	
	@Autowired
	@Qualifier("umsRoleDao")
	private UMSRoleDao umsRoleDao;
	
	@Autowired
	@Qualifier("mailSender")
	private JavaMailSender mailSender;
	
	@Override
	public UMSUser findUserById(Long id) {
		// TODO Auto-generated method stub
		return umsUserDao.getById(id);
	}
	
	@Override
	public UMSUser findUserByUUID(String UUID){
		return umsUserDao.findByUUID(UUID);
	}

	@Override
	public UMSUser findUserByName(String name) {
		return umsUserDao.findByName(name);
	}

	@Override
	public List<UMSUser> findAll() {
		// TODO Auto-generated method stub
		return umsUserDao.findAll();
	}

	@Override
	public void deleteUser(Long id) {
		// TODO Auto-generated method stub
		umsUserDao.delete(id);
	}
	
	@Override
	public Long addUser(UMSUserForm userForm) {
		UMSUser user = new UMSUser();
		user.setUserName(userForm.getUserName());
		user.setEmail(userForm.getEmail());
		user.setPassword(DigestUtils.sha256Hex(userForm.getPassword()));
		
		user.setLoginTime(UTCTimeUtil.getUTCTime());
		List<Long> roleIds = userForm.getAssignedRoles();
		List<UMSRole> roleList = new ArrayList<UMSRole>();
		for(Long roldId : roleIds){
			UMSRole role = umsRoleDao.getById(roldId);
			roleList.add(role);
		}
		UMSRole defaultRole=umsRoleDao.getById(DEFAULT_ROLE_ID);
		roleList.add(defaultRole);
		user.setRoleList(roleList);
		if(ConstantsUtil.SETPASSWORD_BYEMAIL.equals(userForm.getSetPwdByEmail())){
			user.setResetPassUUID(UUID.randomUUID().toString());
			SimpleMailMessage mailMessage = new SimpleMailMessage();  
			mailMessage.setTo(userForm.getEmail());  
	        mailMessage.setFrom(ConstantsUtil.UMS_MAIL_SERVER);  
	        mailMessage.setSubject("UMS-Admin Change Password");  
	        mailMessage.setText("https://cas.paxdata.com:8443/umsAdmin/public/toSetPassword/"+user.getResetPassUUID());
	        mailSender.send(mailMessage);
		}
		umsUserDao.save(user);
		return user.getUserId();
	}
	
	@Override
	public void forgetPwd(UMSUserForm userForm){
		UMSUser user=umsUserDao.findByName(userForm.getUserName());
		user.setResetPassUUID(UUID.randomUUID().toString());
		SimpleMailMessage mailMessage = new SimpleMailMessage();  
		mailMessage.setTo(user.getEmail());
		mailMessage.setFrom(ConstantsUtil.UMS_MAIL_SERVER);  
		mailMessage.setSubject("UMS-Admin Change Password"); 
		mailMessage.setText("https://cas.paxdata.com:8443/umsAdmin/public/toSetPassword/"+user.getResetPassUUID());
		mailSender.send(mailMessage);
		umsUserDao.save(user);
		
	}

	@Override
	public void updateUser(UMSUserForm userForm) {
		UMSUser user = umsUserDao.getById(userForm.getUserId());
		user.setEmail(userForm.getEmail());
		List<Long> roleIds = userForm.getAssignedRoles();
		List<UMSRole> roleList = new ArrayList<UMSRole>();
		if(roleIds!=null){
			for(Long roldId : roleIds){
				UMSRole role = umsRoleDao.getById(roldId);
				roleList.add(role);
			}
			UMSRole defaultRole=umsRoleDao.getById(DEFAULT_ROLE_ID);
			roleList.add(defaultRole);
			user.setRoleList(roleList);
		}		
		umsUserDao.update(user);
	}
	
	@Override
	public void setPwd(UMSUserForm userForm){
		UMSUser user=umsUserDao.findByUUID(userForm.getUUID());
		user.setPassword(DigestUtils.sha256Hex(userForm.getPassword()));
		user.setResetPassUUID(null);
	}
	
	@Override
	public void changeUserPwd(UMSUserForm userForm){
		UMSUser user = umsUserDao.getById(userForm.getUserId());
		user.setPassword(DigestUtils.sha256Hex(userForm.getPassword()));
		umsUserDao.update(user);
	}
	
	@Override
	public boolean isExisted(String userName) {
		return umsUserDao.isExisted(userName);
	}
	
	@Override
	public boolean isExisted(Long userId){
		return umsUserDao.isExisted(userId);
	}

	@Override
	public PageModel<UMSUserVo> pageQueryOfVo(QueryUserForm queryUserForm) {
		long totalCount = umsUserDao.queryCount(queryUserForm);
		int pageNum = queryUserForm.getPageNum();
		int pageSize = queryUserForm.getNumPerPage();
		int pageStart = PageUtil.getPageSize(totalCount, pageNum, pageSize);
		List<UMSUser> umsUsers = umsUserDao.pageQuery(pageStart, pageSize, queryUserForm);
		List<UMSUserVo> userVos = convertoVo(umsUsers);
		PageModel<UMSUserVo> pageModel = new PageModel<UMSUserVo>(totalCount,
				pageNum,pageSize,userVos);
		return pageModel;
	}
	
	private List<UMSUserVo> convertoVo(List<UMSUser> users){
		List<UMSUserVo> vos = new ArrayList<UMSUserVo>();
		for(UMSUser user : users){
			UMSUserVo vo = new UMSUserVo();
			vo.setUserId(user.getUserId());
			vo.setUserName(user.getUserName());
			vo.setEmail(user.getEmail());
			vo.setLoginTime(user.getLoginTime());
			vo.setIsSys(user.getIsSys());
			vos.add(vo);
		}
		return vos;
	}

	@Override
	public Set<String> searchUserHints(String input) {
		Set<String> set = new HashSet<String>();
		List<UMSUser> users = umsUserDao.blurSearch(input);
		if(users.size() > 0){
			for(UMSUser user : users){
				if(user.getUserName().contains(input)){
					set.add(user.getUserName());
				}else if(user.getEmail().contains(input)){
					set.add(user.getEmail());
				}
			}
		}
		return set;
	}

	@Override
	public void updateLoginTime(Long userId) {
		Date date = new Date();
		UMSUser user = umsUserDao.getById(userId);
		user.setLoginTime(date);
		umsUserDao.update(user);
	}
	
	
}
