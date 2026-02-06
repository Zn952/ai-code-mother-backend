package com.zn.aicodemother.langgraph4j.ai;

import com.zn.aicodemother.langgraph4j.model.QualityResult;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

/**
 * @program: ai-code-mother-backend
 * @description: 代码质量检查服务
 * @author: Zn
 * @create: 2026-02-06 17:05
 **/
public interface CodeQualityCheckService {

    /**
     * 检查代码质量
     * AI 会分析代码并返回质量检查结果
     */
    @SystemMessage(fromResource = "prompt/code-quality-check-system-prompt.txt")
    QualityResult checkCodeQuality(@UserMessage String codeContent);
}