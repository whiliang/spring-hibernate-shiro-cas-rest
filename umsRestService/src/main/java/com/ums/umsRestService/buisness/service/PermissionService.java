package com.ums.umsRestService.buisness.service;

import java.util.List;

public interface PermissionService {
    //通过一个角色名称的集合，找到权限的并集
    public List<String> getPermissionNames(String userName,Long appId);
}
