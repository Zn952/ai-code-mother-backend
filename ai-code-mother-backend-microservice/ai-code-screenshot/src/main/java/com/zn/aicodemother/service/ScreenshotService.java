package com.zn.aicodemother.service;

/**
 * @program: ai-code-mother-backend
 * @description: 浏览器截图服务
 * @author: Zn
 * @create: 2026-02-01 21:38
 **/
public interface ScreenshotService {

    /**
     * 通用的截图服务，可以得到访问地址
     *
     * @param webUrl 网址
     * @return
     */
    String generateAndUploadScreenshot(String webUrl);

}
