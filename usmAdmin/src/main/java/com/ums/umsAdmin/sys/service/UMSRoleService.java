package com.ums.umsAdmin.sys.service;

import java.util.List;
import java.util.Set;

import com.ums.umsAdmin.sys.form.QueryRoleForm;
import com.ums.umsAdmin.sys.form.UMSRoleForm;
import com.ums.umsAdmin.sys.model.UMSRole;
import com.ums.umsAdmin.sys.util.PageModel;
import com.ums.umsAdmin.sys.vo.UMSRoleVo;

public interface UMSRoleService {
    /**
     * 查询除default以外的所有角色
     * @return List<UMSRole> 不包含default角色
     */
    public List<UMSRole> findAllNoDefault();

    /**
     * 查询角色，返回分页用户数据
     * @param queryRoleForm
     * @return PageModel 内含改页的用户列表
     */
    public PageModel<UMSRoleVo> pageQueryOfVo(QueryRoleForm queryRoleForm);

    /**
     * 查询用户时提供联想提示功能
     * @param input
     * @return
     */
    public Set<String> searchUserHints(String input);

    /**
     * 添加角色
     * @param roleForm 角色表单填充数据
     * @return roleId
     */
    public Long addRole(UMSRoleForm roleForm);

    /**
     * 通过用户id取得角色
     * @param id
     * @return UMSRole
     */
    public UMSRole findRoleById(Long id);

    /**
     * 通过角色名取得角色
     * @param name
     * @return UMSRole
     */
    public UMSRole findRoleByName(String name);

    /**
     * 通过角色id删除该角色
     * @param id
     */
    public void deleteRole(Long id);

    /**
     * 判断角色名是否存在
     * @param roleId
     *  @return
     */
    public boolean isExisted(Long roleId);

    /**
     * 判断同应用下角色名是否存在
     * @param roleName,appId
     *  @return
     */
    public boolean checkRoleName(String roleName,Long appId );

    /**
     * 更新角色
     * @param roleForm
     */
    public void updateRole(UMSRoleForm roleForm);
}
