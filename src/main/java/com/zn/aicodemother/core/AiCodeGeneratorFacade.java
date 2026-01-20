package com.zn.aicodemother.core;

import com.zn.aicodemother.ai.AiCodeGeneratorService;
import com.zn.aicodemother.ai.model.HtmlCodeResult;
import com.zn.aicodemother.ai.model.MultiFileCodeResult;
import com.zn.aicodemother.exception.BusinessException;
import com.zn.aicodemother.exception.ErrorCode;
import com.zn.aicodemother.exception.ThrowUtils;
import com.zn.aicodemother.model.enums.CodeGenTypeEnum;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @program: ai-code-mother-backend
 * @description: AI 代码生成门面类，组合代码生成和保存功能 门面模式
 * @author: Zn
 * @create: 2026-01-20 18:12
 **/
@Service
public class AiCodeGeneratorFacade {

    @Resource
    private AiCodeGeneratorService aiCodeGeneratorService;

    /**
     * 根据用户输入的消息和生成类型生成代码并保存到指定位置
     *
     * @param userMessage     用户输入的消息，用于生成代码的内容和结构
     * @param codeGenTypeEnum 生成类型，用于选择生成代码的方式
     * @return 返回生成的文件对象
     */
    public File generateAndSaveCode(String userMessage, CodeGenTypeEnum codeGenTypeEnum) {
        ThrowUtils.throwIf(codeGenTypeEnum == null, ErrorCode.PARAMS_ERROR, "生成类型为空");
        return switch (codeGenTypeEnum) {
            case HTML -> generateAndSaveHtmlCode(userMessage);
            case MULTI_FILE -> generateAndSaveMultiFileCode(userMessage);
            default -> {
                String errorMessage = "不支持的生成类型：" + codeGenTypeEnum;
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, errorMessage);
            }
        };
    }

    /**
     * 生成并保存多文件代码的方法
     * 此方法根据用户输入的消息生成相应的代码文件并保存到指定位置
     *
     * @param userMessage 用户输入的消息，用于生成代码的内容和结构
     * @return 返回生成的File对象，表示保存的文件
     */
    private File generateAndSaveMultiFileCode(String userMessage) {
        MultiFileCodeResult result = aiCodeGeneratorService.generateMultiFileCode(userMessage);
        return CodeFileSaver.saveMultiFileCodeResult(result);
    }

    /**
     * 生成并保存HTML代码的方法
     *
     * @param userMessage 用户输入的消息内容，将用于生成HTML
     * @return 返回生成的HTML文件对象
     */
    private File generateAndSaveHtmlCode(String userMessage) {
        HtmlCodeResult result = aiCodeGeneratorService.generateHtmlCode(userMessage);
        return CodeFileSaver.saveHtmlCodeResult(result);
    }
}
