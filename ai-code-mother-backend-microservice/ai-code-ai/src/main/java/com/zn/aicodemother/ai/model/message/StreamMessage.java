package com.zn.aicodemother.ai.model.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: ai-code-mother-backend
 * @description: 流式消息的基类
 * @author: Zn
 * @create: 2026-01-31 15:07
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StreamMessage {

    private String type;
}

