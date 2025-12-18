package com.se.okr_agile.common;

import lombok.Data;

@Data
public class Result<T> {
    private int code;
    private String message;
    private T data;

    private Result() {}

    private Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // 成功：无数据
    public static <T> Result<T> success() {
        return new Result<>(200, "success", null);
    }

    // 成功：有数据
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "success", data);
    }

    // 失败：自定义错误码和消息
    public static <T> Result<T> error(int code, String message) {
        return new Result<>(code, message, null);
    }

    // 失败：通用错误（如 500）
    public static <T> Result<T> error(String message) {
        return new Result<>(500, message, null);
    }
}