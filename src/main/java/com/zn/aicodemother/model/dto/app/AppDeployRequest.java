package com.zn.aicodemother.model.dto.app;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @program: ai-code-mother-backend
 * @description: 部署请求类
 * @author: Zn
 * @create: 2026-01-29 20:57
 **/
@Data
public class AppDeployRequest implements Serializable {


    /**
     * 应用 id
     */
    private Long appId;

    @Serial
    private static final long serialVersionUID = 1L;


}
