package com.zn.aicodemother.model.dto.app;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @program: ai-code-mother-backend
 * @description: 用户更新应用请求实体（只支持修改应用名称）
 * @author: Zn
 * @create: 2026-01-19
 **/
@Data
public class AppUpdateRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 应用名称
     */
    private String appName;

    @Serial
    private static final long serialVersionUID = 1L;
}
