package com.mzl.insta360demo.exception;

import com.mzl.insta360demo.base.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @ClassName: WebExceptionHandler
 * @Description: 统一异常处理器
 * @Author: mzl
 * @CreateDate: 2023/12/4 14:37
 * @Version: 1.0
 */
public class WebExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(WebExceptionHandler.class);

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Response<Void>> handleBusinessException(BusinessException exception) {
        logger.error("business exception", exception);
        Response<Void> result = new Response(exception.getCode(), exception.getMessage());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<Void>> handleSystemException(Exception exception) {
        logger.error("system exception", exception);
        Response<Void> result = new Response(-1, exception.getMessage());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}