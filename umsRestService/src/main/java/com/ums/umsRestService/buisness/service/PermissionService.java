package com.ums.umsRestService.buisness.service;

import java.util.List;

public interface PermissionService {
    //ͨ��һ����ɫ���Ƶļ��ϣ��ҵ�Ȩ�޵Ĳ���
    public List<String> getPermissionNames(String userName,Long appId);
}
