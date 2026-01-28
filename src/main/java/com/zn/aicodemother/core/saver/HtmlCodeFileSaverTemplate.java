package com.zn.aicodemother.core.saver;

import cn.hutool.core.text.CharSequenceUtil;
import com.zn.aicodemother.ai.model.HtmlCodeResult;
import com.zn.aicodemother.exception.BusinessException;
import com.zn.aicodemother.exception.ErrorCode;
import com.zn.aicodemother.model.enums.CodeGenTypeEnum;

/**
 * @program: ai-code-mother-backend
 * @description: HTML代码文件保存器
 * @author: Zn
 * @create: 2026-01-24 22:50
 **/
public class HtmlCodeFileSaverTemplate extends CodeFileSaverTemplate<HtmlCodeResult> {

    @Override
    protected CodeGenTypeEnum getCodeType() {
        return CodeGenTypeEnum.HTML;
    }

    @Override
    protected void saveFiles(HtmlCodeResult result, String baseDirPath) {
        // 保存 HTML 文件
        writeToFile(baseDirPath, "index.html", result.getHtmlCode());
    }

    @Override
    protected void validateInput(HtmlCodeResult result) {
        super.validateInput(result);
        // HTML 代码不能为空
        if (CharSequenceUtil.isBlank(result.getHtmlCode())) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "HTML代码内容不能为空");
        }
    }
}

