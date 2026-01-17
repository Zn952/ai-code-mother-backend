package com.zn.aicodemother.service.impl;

import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zn.aicodemother.exception.ErrorCode;
import com.zn.aicodemother.exception.ThrowUtils;
import com.zn.aicodemother.mapper.UserMapper;
import com.zn.aicodemother.model.entity.User;
import com.zn.aicodemother.model.enums.UserRoleEnum;
import com.zn.aicodemother.model.vo.LoginUserVO;
import com.zn.aicodemother.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

import static com.zn.aicodemother.constant.UserConstant.USER_LOGIN_STATE;


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
     * @param userPassword      用户密码，需要加密存储
     * @param checkPassword 确认密码，用于验证两次输入的密码是否一致
     * @return 返回用户ID
     */
    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        //1、校验参数是否为空
        ThrowUtils.throwIf(StrUtil.hasBlank(userAccount, userPassword, checkPassword), ErrorCode.PARAMS_ERROR, "参数为空");
        //2、校验参数是否符合要求
        ThrowUtils.throwIf(userAccount.length() < 4 || userAccount.length() > 20, ErrorCode.PARAMS_ERROR, "用户名长度不符合要求");
        ThrowUtils.throwIf(userPassword.length() < 8 || userPassword.length() > 20, ErrorCode.PARAMS_ERROR, "密码长度不符合要求");
        ThrowUtils.throwIf(!userPassword.equals(checkPassword), ErrorCode.PARAMS_ERROR, "两次密码输入不一致");
        //3、校验用户是否已存在
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userAccount", userAccount);
        long count = this.mapper.selectCountByQuery(queryWrapper);
        ThrowUtils.throwIf(count > 0, ErrorCode.PARAMS_ERROR, "用户已存在");
        //4、密码加密
        String encryptedPassword = this.getEncryptedPassword(userPassword);
        //5、注册用户
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptedPassword);
        user.setUserName("无名");
        user.setUserRole(UserRoleEnum.USER.getValue());
        boolean saved = this.save(user);
        ThrowUtils.throwIf(!saved, ErrorCode.OPERATION_ERROR, "注册失败，数据库错误");
        return user.getId();
    }


    /**
     * 加密密码的方法
     *
     * @param userPassword 原始密码字符串
     * @return 加密后的密码字符串
     */
    @Override
    public String getEncryptedPassword(String userPassword) {
        // 盐值，混淆密码
        final String SALT = "zn";
        return DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request      请求
     * @return 脱敏后的用户信息
     */
    @Override
    public LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        //1、校验参数是否为空
        ThrowUtils.throwIf(StrUtil.hasBlank(userAccount, userPassword), ErrorCode.PARAMS_ERROR, "参数为空");
        //2、校验参数是否符合要求
        ThrowUtils.throwIf(userAccount.length() < 4 || userAccount.length() > 20, ErrorCode.PARAMS_ERROR, "用户名长度不符合要求");
        ThrowUtils.throwIf(userPassword.length() < 8 || userPassword.length() > 20, ErrorCode.PARAMS_ERROR, "密码长度不符合要求");
        //3、加密密码
        String encryptedPassword = getEncryptedPassword(userPassword);
        //4、验证用户是否存在
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptedPassword);
        User user = this.mapper.selectOneByQuery(queryWrapper);
        ThrowUtils.throwIf(user == null, ErrorCode.PARAMS_ERROR, "用户不存在或密码错误");
        //5、记录登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, user);
        //6、获取脱敏后的用户信息
        return this.getLoginUserVO(user);
    }

    /**
     * 获取脱敏的已登录用户信息
     *
     * @param user 用户实体对象，包含用户的详细信息
     * @return LoginUserVO 脱敏后的用户信息
     */
    @Override
    public LoginUserVO getLoginUserVO(User user) {
        if (user == null) {
            return null;
        }
        LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtils.copyProperties(user, loginUserVO);
        return loginUserVO;
    }

    /**
     * 获取当前登录用户
     *
     * @param request 请求
     * @return 脱敏后的用户信息
     */
    @Override
    public User getLoginUser(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        ThrowUtils.throwIf(user == null || user.getId()==null, ErrorCode.NOT_LOGIN_ERROR);
        return user;
    }
}
