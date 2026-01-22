package com.zn.aicodemother.parser;

/**
 * @program: ai-code-mother-backend
 * @description: 代码解析器策略接口
 * @author: Zn
 * @create: 2026-01-22 21:40
 **/
public interface CodeParser<T> {

    /**
     * 解析代码内容
     *
     * @param codeContent 原始代码内容
     * @return 解析后的结果对象
     */
    T parseCode(String codeContent);
}

