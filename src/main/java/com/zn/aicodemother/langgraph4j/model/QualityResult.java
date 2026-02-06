package com.zn.aicodemother.langgraph4j.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @program: ai-code-mother-backend
 * @description: 质量检查
 * @author: Zn
 * @create: 2026-02-06 15:32
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QualityResult implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 是否通过质检
     */
    private Boolean isValid;

    /**
     * 错误列表
     */
    private List<String> errors;

    /**
     * 改进建议
     */
    private List<String> suggestions;
}
