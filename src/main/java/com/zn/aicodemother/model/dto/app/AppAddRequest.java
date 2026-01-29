package com.zn.aicodemother.model.dto.app;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @program: ai-code-mother-backend
 * @description: 用户创建应用请求实体
 * @author: Zn
 * @create: 2026-01-19
 **/
@Data
public class AppAddRequest implements Serializable {

    /**
     * 应用初始化的 prompt
     */
    private String initPrompt;

    @Serial
    private static final long serialVersionUID = 1L;
}
