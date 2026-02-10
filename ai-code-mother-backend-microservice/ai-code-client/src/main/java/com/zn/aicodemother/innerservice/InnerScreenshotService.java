package com.zn.aicodemother.innerservice;

/**
 * @program: ai-code-mother-backend-microservice
 * @description: 内部接口的截图服务
 * @author: Zn
 * @create: 2026-02-10 20:57
 **/
public interface InnerScreenshotService {

    String generateAndUploadScreenshot(String webUrl);

}
