package com.zn.aicodemother.langgraph4j.node;

import com.zn.aicodemother.langgraph4j.model.ImageResource;
import com.zn.aicodemother.langgraph4j.model.enums.ImageCategoryEnum;
import com.zn.aicodemother.langgraph4j.state.WorkflowContext;
import lombok.extern.slf4j.Slf4j;
import org.bsc.langgraph4j.action.AsyncNodeAction;
import org.bsc.langgraph4j.prebuilt.MessagesState;

import java.util.Arrays;
import java.util.List;

import static org.bsc.langgraph4j.action.AsyncNodeAction.node_async;

/**
 * @program: ai-code-mother-backend
 * @description: 代码生成节点
 * @author: Zn
 * @create: 2026-02-04 20:56
 **/
@Slf4j
public class CodeGeneratorNode {
    public static AsyncNodeAction<MessagesState<String>> create() {
        return node_async(state -> {
            WorkflowContext context = WorkflowContext.getContext(state);
            log.info("执行节点: 代码生成");

            // TODO: 实际执行代码生成逻辑

            // 简单的假数据
            String generatedCodeDir = "/tmp/generated/fake-code";
            // 更新状态
            context.setCurrentStep("代码生成");
            context.setGeneratedCodeDir(generatedCodeDir);
            log.info("代码生成完成，目录: {}", generatedCodeDir);
            return WorkflowContext.saveContext(context);
        });
    }
}

