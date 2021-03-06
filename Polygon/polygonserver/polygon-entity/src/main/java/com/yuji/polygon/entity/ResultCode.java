package com.yuji.polygon.entity;

/**
 * @className: ResultCode
 * @description:
 * @author: yuji
 * @create: 2020-08-05 22:50
 **/

public enum ResultCode {

    SUCCESS(1000, "响应成功"),

    FAILED(1001, "响应失败"),

    VALIDATE_FAILED(1002, "参数校验失败"),

    ERROR(5000, "未知错误");

    private int code;

    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
