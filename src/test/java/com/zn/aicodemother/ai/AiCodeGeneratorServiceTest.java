package com.zn.aicodemother.ai;

import com.zn.aicodemother.ai.model.HtmlCodeResult;
import com.zn.aicodemother.ai.model.MultiFileCodeResult;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AiCodeGeneratorServiceTest {

    @Resource
    private AiCodeGeneratorService aiCodeGeneratorService;

    @Test
    void generateHtmlCode() {
        HtmlCodeResult generatedHtmlCode = aiCodeGeneratorService.generateHtmlCode("做个记账统计的小工具,50行代码");
        Assertions.assertNotNull(generatedHtmlCode);
    }

    @Test
    void generateMultiFileCode() {
        MultiFileCodeResult generateMultiFileCode = aiCodeGeneratorService.generateMultiFileCode("做个记账统计的小工具，50行代码");
        Assertions.assertNotNull(generateMultiFileCode);
    }
}