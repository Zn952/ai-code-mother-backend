package com.zn.aicodemother.langgraph4j.ai;

import com.zn.aicodemother.langgraph4j.model.ImageCollectionPlan;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

/**
 * @program: ai-code-mother-backend
 * @description: 图片收集规划服务
 * @author: Zn
 * @create: 2026-02-06 21:45
 **/
public interface ImageCollectionPlanService {

    /**
     * 根据用户提示词分析需要收集的图片类型和参数
     */
    @SystemMessage(fromResource = "prompt/image-collection-plan-system-prompt.txt")
    ImageCollectionPlan planImageCollection(@UserMessage String userPrompt);
}

