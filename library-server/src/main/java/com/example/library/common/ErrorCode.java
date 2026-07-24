package com.example.library.common;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    BAD_REQUEST(40000, HttpStatus.BAD_REQUEST, "请求参数不正确"),
    UNAUTHORIZED(40100, HttpStatus.UNAUTHORIZED, "请先登录"),
    FORBIDDEN(40300, HttpStatus.FORBIDDEN, "无权访问"),
    NOT_FOUND(40400, HttpStatus.NOT_FOUND, "数据不存在"),
    CONFLICT(40900, HttpStatus.CONFLICT, "数据状态冲突"),
    NOT_IMPLEMENTED(50100, HttpStatus.NOT_IMPLEMENTED, "学习任务尚未实现"),
    INTERNAL_ERROR(50000, HttpStatus.INTERNAL_SERVER_ERROR, "服务器内部错误");

    private final int code;
    private final HttpStatus status;
    private final String message;

    ErrorCode(int code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

    public int code() {
        return code;
    }

    public HttpStatus status() {
        return status;
    }

    public String message() {
        return message;
    }
}
