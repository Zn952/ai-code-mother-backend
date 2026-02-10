package com.zn.aicodemother.common;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @program: ai-code-mother-backend
 * @description: 删除请求包装类，仅包含要删除的ID
 * @author: Zn
 * @create: 2026-01-16 21:58
 **/
@Data
public class DeleteRequest implements Serializable {

    /**
     * id
     */
    private Long id;


    @Serial
    private static final long serialVersionUID = 1L;
}
