package com.ums.umsAdmin.sys.service;

import java.util.List;
import java.util.Set;

import com.ums.umsAdmin.sys.form.QueryUserForm;
import com.ums.umsAdmin.sys.form.UMSUserForm;
import com.ums.umsAdmin.sys.model.UMSUser;
import com.ums.umsAdmin.sys.util.PageModel;
import com.ums.umsAdmin.sys.vo.UMSUserVo;

public interface UMSUserService {
    /**
     * 通过用户id取得用户
     * @param id
     * @return UMSUser
     */
    public UMSUser findUserById(Long id);


    /**
     * 通过用户UUID取得用户
     * @param UUID
     * @return UMSUser
     */
    public UMSUser findUserByUUID(String UUID);
    /**
     * 通过用户姓名取得用户
     * @param name
     * @return UMSUser
     */
    public UMSUser findUserByName(String name);
    /**
     * 取得所有用户
     * @return UMSUsers' list
     */
    public List<UMSUser> findAll();
    /**
     * 通过用户id删除该用户
     * @param id
     */
    public void deleteUser(Long id);
    /**
     * 添加用户
     * @param userForm 用户表单填充数据
     * @return user's id
     */
    public Long addUser(UMSUserForm userForm);
    /**
     * 更新用户
     * @param userForm
     */
    public void updateUser(UMSUserForm userForm);
    /**
     * 判断用户名是否存在
     * @param userName
     * @return
     */
    public boolean isExisted(String userName);

    /**
     * 判断用户名是否存在
     * @param userId
     *  @return
     */
    public boolean isExisted(Long userId);
    /**
     * 查询用户，返回分页用户数据
     * @param queryUserForm
     * @return PageModel 内含改页的用户列表
     */
    public PageModel<UMSUserVo> pageQueryOfVo(QueryUserForm queryUserForm);
    /**
     * 查询用户时提供联想提示功能
     * @param input
     * @return
     */
    public Set<String> searchUserHints(String input);
    /**
     * 更改用户密码
     * @param userForm
     */
    public void changeUserPwd(UMSUserForm userForm);

    /**
     * 设置密码
     * @param userForm
     */
    public void setPwd(UMSUserForm userForm);

    /**
     * 忘记密码
     * @param userForm
     */
    public void forgetPwd(UMSUserForm userForm);

    /** 更新登录时间
     * @param userId
     */
    public void updateLoginTime(Long userId);
}
