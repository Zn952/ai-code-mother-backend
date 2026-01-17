package com.zn.aicodemother.service;

import com.mybatisflex.core.service.IService;
import com.zn.aicodemother.model.entity.User;

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
     * @param password      用户密码，需要加密存储
     * @param checkPassword 确认密码，用于验证两次输入的密码是否一致
     * @return 返回用户ID
     */
    long userRegister(String userAccount, String password, String checkPassword);

    /**
     * 加密密码的方法
     *
     * @param password 原始密码字符串
     * @return 加密后的密码字符串
     */
    String getEncryptedPassword(String password);
}
