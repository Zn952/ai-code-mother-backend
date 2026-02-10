package com.zn.aicodemother.model.dto.app;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @program: ai-code-mother-backend
 * @description: 管理员更新应用请求实体（支持更新应用名称、应用封面、优先级）
 * @author: Zn
 * @create: 2026-01-19
 **/
@Data
public class AppAdminUpdateRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 应用封面
     */
    private String cover;

    /**
     * 优先级
     */
    private Integer priority;

    @Serial
    private static final long serialVersionUID = 1L;
}
