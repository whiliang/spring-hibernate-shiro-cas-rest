package com.ums.umsAdmin.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ums.umsAdmin.sys.dao.UMSAppDao;
import com.ums.umsAdmin.sys.model.UMSApp;
import com.ums.umsAdmin.sys.service.UMSAppService;
@Service("umsAppService")
public class UMSAppServiceImpl implements UMSAppService{
	@Autowired
	@Qualifier("umsAppDao")
	private UMSAppDao umsAppDao;
	
	@Override
	public List<UMSApp> findAll(){
		return umsAppDao.findAll();
	}
	
	@Override
	public UMSApp findAppById(Long id){
		return umsAppDao.getById(id);
	}
}
