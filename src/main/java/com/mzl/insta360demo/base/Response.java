package com.mzl.insta360demo.base;

import lombok.Data;
import java.util.Objects;

/**
 * @ClassName: Response
 * @Description: 统一响应返回体
 * @Author: mzl
 * @CreateDate: 2023/12/4 13:40
 * @Version: 1.0
 */
@Data
public class Response<T> {

    /**
     * 成功状态
     */
    public static final Integer SUCCESS_CODE = 0;

    /**
     * 失败状态
     */
    public static final Integer FAIL_CODE = -1;
    /**
     * 响应码
     */
    private int code;

    /**
     * 响应消息
     */
    private String msg;

    /**
     * 响应体（消息体）
     */
    private T data;

    public Response() {
    }

    public Response(int code, String message) {
        this.code = code;
        this.msg = message;
    }

    public Response(int code, String message, T data) {
        this.code = code;
        this.msg = message;
        this.data = data;
    }

    public Response<T> setData(T data) {
        this.data = data;
        return this;
    }

    public boolean isSuccess() {
        return Objects.equals(this.getCode(), SUCCESS_CODE);
    }

    public boolean isFail() {
        return !isSuccess();
    }

    public static <T> Response<T> success() {
        return new Response<>(SUCCESS_CODE, "操作成功");
    }

    public static <T> Response<T> success(String message) {
        return new Response<>(SUCCESS_CODE, message);
    }

    public static <T> Response<T> success(T data) {
        return new Response<>(SUCCESS_CODE, "操作成功", data);
    }

    public static <T> Response<T> success(String message, T data) {
        return new Response<>(SUCCESS_CODE, message, data);
    }

    public static <T> Response<T> fail() {
        return new Response<>(FAIL_CODE, "操作失败");
    }

    public static <T> Response<T> fail(String msg) {
        return new Response<>(FAIL_CODE, msg);
    }

}