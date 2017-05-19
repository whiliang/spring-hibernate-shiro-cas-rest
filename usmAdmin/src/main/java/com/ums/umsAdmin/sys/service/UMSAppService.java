package com.ums.umsAdmin.sys.service;

import java.util.List;

import com.ums.umsAdmin.sys.model.UMSApp;

public interface UMSAppService {
    /**
     * 获取所有应用
     *
     * @return list<UMSApp>
     */
    public List<UMSApp> findAll();

    /**
     * 通过用户id取得应用
     *
     * @param id
     * @return UMSApp
     */
    public UMSApp findAppById(Long id);

}
