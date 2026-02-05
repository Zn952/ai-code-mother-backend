package com.zn.aicodemother.langgraph4j.tools;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.dashscope.aigc.imagegeneration.*;
import com.alibaba.dashscope.utils.Constants;
import com.zn.aicodemother.langgraph4j.model.ImageResource;
import com.zn.aicodemother.langgraph4j.model.enums.ImageCategoryEnum;
import com.zn.aicodemother.manager.CosManager;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @program: ai-code-mother-backend
 * @description: Logo图片生成工具
 * @author: Zn
 * @create: 2026-02-05 19:22
 **/
@Slf4j
@Component
public class LogoGeneratorTool {

    static {
        Constants.baseHttpApiUrl = "https://dashscope.aliyuncs.com/api/v1";
    }

    @Resource
    private CosManager cosManager;

    @Value("${dashscope.api-key:}")
    private String dashScopeApiKey;

    @Value("${dashscope.image-model:}")
    private String imageModel;

    @Tool("根据描述生成 Logo 设计图片，用于网站品牌标识")
    public List<ImageResource> generateLogos(@P("Logo 设计描述，如名称、行业、风格等，尽量详细") String description) {

        List<ImageResource> logoList = new ArrayList<>();
        try {
            // 构建 Logo 设计提示词
            String logoPrompt = String.format("生成 Logo，Logo 中禁止包含任何文字！Logo 介绍：%s", description);
            ImageGenerationMessage message = ImageGenerationMessage.builder()
                    .role("user")
                    .content(Collections.singletonList(Collections.singletonMap("text", logoPrompt)))
                    .build();
            ImageGenerationParam param = ImageGenerationParam.builder()
                    .apiKey(dashScopeApiKey)
                    .model(imageModel)
                    .size("1280*1280")
                    .n(1) // 生成 1 张足够，因为 AI 不知道哪张最好
                    .negativePrompt("")
                    .promptExtend(true)
                    .watermark(false)
                    .messages(Collections.singletonList(message))
                    .build();
            ImageGeneration imageGeneration = new ImageGeneration();
            ImageGenerationResult result = imageGeneration.call(param);
            if (result != null && result.getOutput() != null && result.getOutput().getChoices() != null) {
                for (ImageGenerationOutput.Choice choice : result.getOutput().getChoices()) {
                    if (choice.getMessage() != null && choice.getMessage().getContent() != null) {
                        List<Map<String, Object>> results = choice.getMessage().getContent();
                        for (Map<String, Object> imageResult : results) {
                            String imageUrl = imageResult.get("image").toString();
                            imageUrl = downloadAndUploadImage(imageUrl);
                            if (CharSequenceUtil.isNotBlank(imageUrl)) {
                                logoList.add(ImageResource.builder()
                                        .category(ImageCategoryEnum.LOGO)
                                        .description(description)
                                        .url(imageUrl)
                                        .build());
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("生成 Logo 失败: {}", e.getMessage(), e);
        }
        return logoList;
    }

       /* String imageUrl = "https://www.codefather.cn/_next/image?url=/images/logo.png&w=256&q=75";
        List<ImageResource> logoList = new ArrayList<>();
        logoList.add(ImageResource.builder()
                .category(ImageCategoryEnum.LOGO)
                .description(description)
                .url(imageUrl)
                .build());
        return logoList;
    }

        */

    private String downloadAndUploadImage(String imageUrl) {
        try {
            // 创建临时目录
            Path tempDir = Paths.get(System.getProperty("user.dir") + "/tmp/logos");
            if (!Files.exists(tempDir)) {
                Files.createDirectories(tempDir);
            }

            // 生成临时文件名
            String timestamp = String.valueOf(System.currentTimeMillis());
            String fileName = "logo_" + timestamp + ".png";
            Path tempFilePath = tempDir.resolve(fileName);

            // 从URL下载图片
            log.info("开始下载图片: {}", imageUrl);
            try (java.io.InputStream in = new URL(imageUrl).openStream()) {
                Files.copy(in, tempFilePath);
            }

            // 上传到COS
            log.info("开始上传图片到COS: {}", tempFilePath);
            File file = tempFilePath.toFile();
            String keyName = String.format("/logos/%s/%s",
                    RandomUtil.randomString(5), file.getName());
            String uploadedUrl = cosManager.uploadFile(keyName, tempFilePath.toFile());

            // 删除临时文件
//            Files.deleteIfExists(tempFilePath);

            return uploadedUrl;

        } catch (Exception e) {
            log.error("图片下载或上传失败: {}", e.getMessage(), e);
            return null;
        }
    }
}


