package com.zn.aicodemother.model.dto.user;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @program: ai-code-mother-backend
 * @description: 用户更新请求
 * @author: Zn
 * @create: 2026-01-19 11:46
 **/
@Data
public class UserUpdateRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 简介
     */
    private String userProfile;

    /**
     * 用户角色：user/admin
     */
    private String userRole;

    @Serial
    private static final long serialVersionUID = 1L;
}

