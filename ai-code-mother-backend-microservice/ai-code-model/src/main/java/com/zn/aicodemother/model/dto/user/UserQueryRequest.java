package com.zn.aicodemother.model.dto.user;

import com.zn.aicodemother.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * @program: ai-code-mother-backend
 * @description: 查询用户信息请求实体类
 * @author: Zn
 * @create: 2026-01-19 11:49
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryRequest extends PageRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 简介
     */
    private String userProfile;

    /**
     * 用户角色：user/admin/ban
     */
    private String userRole;

    @Serial
    private static final long serialVersionUID = 1L;
}
