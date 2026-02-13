package com.zn.aicodemother.service;

import jakarta.servlet.http.HttpServletResponse;

/**
 * @program: ai-code-mother-backend
 * @description: 下载代码服务
 * @author: Zn
 * @create: 2026-02-01 21:50
 **/
public interface ProjectDownloadService {

    /**
     * 将指定项目路径下的内容打包成ZIP文件并下载
     *
     * @param projectPath      需要打包下载的项目路径
     * @param downloadFileName 下载时保存的文件名
     * @param response         HTTP响应对象，用于输出下载文件流
     */
    void downloadProjectAsZip(String projectPath, String downloadFileName, HttpServletResponse response);
}
