package com.zn.aicodemother.langgraph4j.node;

import com.zn.aicodemother.ai.AiCodeGenTypeRoutingService;
import com.zn.aicodemother.ai.AiCodeGenTypeRoutingServiceFactory;
import com.zn.aicodemother.langgraph4j.state.WorkflowContext;
import com.zn.aicodemother.model.enums.CodeGenTypeEnum;
import com.zn.aicodemother.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.bsc.langgraph4j.action.AsyncNodeAction;
import org.bsc.langgraph4j.prebuilt.MessagesState;

import static org.bsc.langgraph4j.action.AsyncNodeAction.node_async;

/**
 * @program: ai-code-mother-backend
 * @description: 智能路由节点
 * @author: Zn
 * @create: 2026-02-04 20:54
 **/
@Slf4j
public class RouterNode {

    public static AsyncNodeAction<MessagesState<String>> create() {
        return node_async(state -> {
            WorkflowContext context = WorkflowContext.getContext(state);
            log.info("执行节点: 智能路由");
            CodeGenTypeEnum generationType;
            try {
                // 获取AI路由服务
                AiCodeGenTypeRoutingServiceFactory routingServiceFactory = SpringContextUtil.getBean(AiCodeGenTypeRoutingServiceFactory.class);
                AiCodeGenTypeRoutingService routingService = routingServiceFactory.createAiCodeGenTypeRoutingService();
                // 根据原始提示词进行智能路由
                generationType = routingService.routeCodeGenType(context.getOriginalPrompt());
                log.info("AI智能路由完成，选择类型: {} ({})", generationType.getValue(), generationType.getText());
            } catch (Exception e) {
                log.error("AI智能路由失败，使用默认HTML类型: {}", e.getMessage());
                generationType = CodeGenTypeEnum.HTML;
            }
            // 更新状态
            context.setCurrentStep("智能路由");
            context.setGenerationType(generationType);
            return WorkflowContext.saveContext(context);
        });
    }
}



