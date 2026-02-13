package com.zn.aicodemother.service;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.zn.aicodemother.model.dto.user.UserQueryRequest;
import com.zn.aicodemother.model.entity.User;
import com.zn.aicodemother.model.vo.LoginUserVO;
import com.zn.aicodemother.model.vo.UserVO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

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
     * @param userPassword  用户密码，需要加密存储
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
     * @param request      请求对象
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
     * @param request 请求对象
     * @return 脱敏后的用户信息
     */
    User getLoginUser(HttpServletRequest request);


    /**
     * 用户登出
     *
     * @param request 请求对象
     * @return 登出是否成功
     */
    Boolean userLogout(HttpServletRequest request);

    /**
     * 添加用户的方法
     *
     * @param user 用户对象，包含要添加的用户信息
     * @return 返回添加的用户ID
     */
    Long addUser(User user);

    /**
     * 根据User实体对象获取对应的UserVO对象
     * UserVO用于前端展示，是对User实体的一种封装
     *
     * @param user 用户实体对象，包含完整的用户信息
     * @return 返回对应的UserVO对象
     */
    UserVO getUserVO(User user);

    /**
     * 根据用户列表获取用户视图对象列表
     * 该方法用于将User实体列表转换为UserVO视图对象列表，用于展示层
     *
     * @param userList 用户实体列表，包含完整的用户信息
     * @return UserVO视图对象列表
     */
    List<UserVO> getUserVOList(List<User> userList);

    /**
     * 根据用户查询请求条件构建查询包装器
     *
     * @param userQueryRequest 用户查询请求对象，包含查询条件
     * @return QueryWrapper 返回一个包含查询条件的MyBatis-Plus查询包装器，
     */
    QueryWrapper getQueryWrapper(UserQueryRequest userQueryRequest);
}
