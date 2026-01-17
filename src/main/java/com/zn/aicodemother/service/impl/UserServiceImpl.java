package com.zn.aicodemother.service.impl;

import cn.hutool.core.util.ObjUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zn.aicodemother.exception.BusinessException;
import com.zn.aicodemother.exception.ErrorCode;
import com.zn.aicodemother.mapper.UserMapper;
import com.zn.aicodemother.model.entity.User;
import com.zn.aicodemother.model.enums.UserRoleEnum;
import com.zn.aicodemother.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * 服务层实现。
 *
 * @author Zn
 * @since 2026-01-17
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * 用户注册方法
     *
     * @param userAccount   用户账户，用于注册系统的唯一标识
     * @param password      用户密码，需要加密存储
     * @param checkPassword 确认密码，用于验证两次输入的密码是否一致
     * @return 返回用户ID
     */
    @Override
    public long userRegister(String userAccount, String password, String checkPassword) {
        //1、校验参数是否为空
        if (ObjUtil.hasEmpty(userAccount, password, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        //2、校验参数是否符合要求
        if (userAccount.length() < 4 || userAccount.length() > 20) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名长度不符合要求");
        }
        if (password.length() < 8 || password.length() > 20) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码长度不符合要求");
        }
        if (!password.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次密码输入不一致");
        }
        //3、校验用户是否已存在
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userAccount", userAccount);
        long count = this.mapper.selectCountByQuery(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户已存在");
        }
        //4、密码加密
        String encryptedPassword = this.getEncryptedPassword(password);
        //5、注册用户
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptedPassword);
        user.setUserName("无名");
        user.setUserRole(UserRoleEnum.USER.getValue());
        boolean saved = this.save(user);
        if (!saved) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "注册失败，数据库错误");
        }
        return user.getId();
    }


    /**
     * 加密密码的方法
     *
     * @param password 原始密码字符串
     * @return 加密后的密码字符串
     */
    @Override
    public String getEncryptedPassword(String password) {
        // 盐值，混淆密码
        final String SALT = "zn";
        return DigestUtils.md5DigestAsHex((SALT + password).getBytes(StandardCharsets.UTF_8));
    }
}
