package com.zn.aicodemother.service;

import com.mybatisflex.core.service.IService;
import com.zn.aicodemother.model.entity.User;
import com.zn.aicodemother.model.vo.LoginUserVO;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 服务层。
 *
 * @author Zn
 * @since 2026-01-17
 */
public interface UserService extends IService<User> {


    /**
     * 用户注册方法
     *
     * @param userAccount   用户账户，用于注册系统的唯一标识
     * @param userPassword      用户密码，需要加密存储
     * @param checkPassword 确认密码，用于验证两次输入的密码是否一致
     * @return 返回用户ID
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 加密密码的方法
     *
     * @param userPassword 原始密码字符串
     * @return 加密后的密码字符串
     */
    String getEncryptedPassword(String userPassword);

    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request      请求
     * @return 脱敏后的用户信息
     */
    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);


    /**
     * 获取脱敏的已登录用户信息
     *
     * @param user 用户实体对象，包含用户的详细信息
     * @return 脱敏后的用户信息
     */
    LoginUserVO getLoginUserVO(User user);

    /**
     * 获取当前登录用户
     *
     * @param request 请求
     * @return 脱敏后的用户信息
     */
    User getLoginUser(HttpServletRequest request);


}
