package com.zn.aicodemother.core.saver;

import com.zn.aicodemother.ai.model.HtmlCodeResult;
import com.zn.aicodemother.ai.model.MultiFileCodeResult;
import com.zn.aicodemother.exception.BusinessException;
import com.zn.aicodemother.exception.ErrorCode;
import com.zn.aicodemother.model.enums.CodeGenTypeEnum;

import java.io.File;

/**
 * @program: ai-code-mother-backend
 * @description: 代码文件保存执行器 根据代码生成类型执行相应的保存逻辑
 * @author: Zn
 * @create: 2026-01-24 22:53
 **/
public class CodeFileSaverExecutor {

    private static final HtmlCodeFileSaverTemplate htmlCodeFileSaver = new HtmlCodeFileSaverTemplate();

    private static final MultiFileCodeFileSaverTemplate multiFileCodeFileSaver = new MultiFileCodeFileSaverTemplate();

    /**
     * 执行代码保存
     *
     * @param codeResult  代码结果对象
     * @param codeGenType 代码生成类型
     * @return 保存的目录
     */
    public static File executeSaver(Object codeResult, CodeGenTypeEnum codeGenType) {
        return switch (codeGenType) {
            case HTML -> htmlCodeFileSaver.saveCode((HtmlCodeResult) codeResult);
            case MULTI_FILE -> multiFileCodeFileSaver.saveCode((MultiFileCodeResult) codeResult);
            default -> throw new BusinessException(ErrorCode.SYSTEM_ERROR, "不支持的代码生成类型: " + codeGenType);
        };
    }
}

