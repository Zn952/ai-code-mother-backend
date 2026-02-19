package com.zn.aicodemother.service.impl;

import com.zn.aicodemother.innerservice.InnerScreenshotService;
import com.zn.aicodemother.service.ScreenshotService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @program: ai-code-mother-backend-microservice
 * @description: 截图服务内部通信
 * @author: Zn
 * @create: 2026-02-19 20:44
 **/
@DubboService
@Slf4j
public class InnerScreenshotServiceImpl implements InnerScreenshotService {

    @Resource
    private ScreenshotService screenshotService;

    @Override
    public String generateAndUploadScreenshot(String webUrl) {
        return screenshotService.generateAndUploadScreenshot(webUrl);
    }
}

