package com.zn.aicodemother.common;

import com.zn.aicodemother.exception.ErrorCode;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: ai-code-mother-backend
 * @description: 通用响应类
 * @author: Zn
 * @create: 2026-01-16 21:50
 **/
@Data
public class BaseResponse<T> implements Serializable {

    private int code;

    private T data;

    private String message;

    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public BaseResponse(int code, T data) {
        this(code, data, "");
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage());
    }
}

