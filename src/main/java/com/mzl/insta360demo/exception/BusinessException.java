package com.mzl.insta360demo.exception;


/**
 * @ClassName: BusinessException
 * @Description: 自定义业务异常
 * @Author: mzl
 * @CreateDate: 2023/12/4 14:19
 * @Version: 1.0
 */
public class BusinessException extends RuntimeException {

    private final int code;
    private final String message;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BusinessException(String message) {
        super(message);
        this.code = -1;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

}