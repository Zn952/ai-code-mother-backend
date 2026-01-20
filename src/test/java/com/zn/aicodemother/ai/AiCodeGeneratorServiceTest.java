package com.zn.aicodemother.ai;

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
        String generatedHtmlCode = aiCodeGeneratorService.generateHtmlCode("做个记账统计的小工具,50行代码");
        Assertions.assertNotNull(generatedHtmlCode);
    }

    @Test
    void generateMultiFileCode() {
        String generateMultiFileCode = aiCodeGeneratorService.generateMultiFileCode("做个记账统计的小工具，50行代码");
        Assertions.assertNotNull(generateMultiFileCode);
    }
}