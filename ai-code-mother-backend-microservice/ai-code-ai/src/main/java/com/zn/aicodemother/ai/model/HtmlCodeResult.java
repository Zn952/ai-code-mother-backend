package com.zn.aicodemother.ai.model;

import dev.langchain4j.model.output.structured.Description;
import lombok.Data;

/**
 * @program: ai-code-mother-backend
 * @description: html类型返回结果
 * @author: Zn
 * @create: 2026-01-20 17:36
 **/
@Description("生成 HTML 代码文件的结果")
@Data
public class HtmlCodeResult {

    @Description("HTML代码")
    private String htmlCode;

    @Description("生成代码的描述")
    private String description;
}


