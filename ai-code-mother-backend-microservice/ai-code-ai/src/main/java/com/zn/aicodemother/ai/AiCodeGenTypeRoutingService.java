package com.zn.aicodemother.ai;

import com.zn.aicodemother.model.enums.CodeGenTypeEnum;
import dev.langchain4j.service.SystemMessage;

/**
 * @program: ai-code-mother-backend
 * @description: AI代码生成类型智能路由服务
 * @author: Zn
 * @create: 2026-02-01 22:34
 **/
public interface AiCodeGenTypeRoutingService {
    /**
     * 根据用户需求智能选择代码生成类型
     *
     * @param userPrompt 用户输入的需求描述
     * @return 推荐的代码生成类型
     */
    @SystemMessage(fromResource = "prompt/codegen-routing-system-prompt.txt")
    CodeGenTypeEnum routeCodeGenType(String userPrompt);
}
