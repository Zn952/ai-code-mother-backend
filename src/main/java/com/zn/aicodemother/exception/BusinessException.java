package com.zn.aicodemother.exception;

import lombok.Getter;

/**
 * @program: ai-code-mother-backend
 * @description: 自定义业务异常
 * @author: Zn
 * @create: 2026-01-16 21:46
 **/
@Getter
public class BusinessException extends RuntimeException {

    /**
     * 错误码
     */
    private final int code;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }
}
