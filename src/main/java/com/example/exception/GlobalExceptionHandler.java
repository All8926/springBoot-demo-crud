package com.example.exception;

import com.example.pojo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)  // 捕获所有异常
    public Result ex(Exception ex) {
        ex.printStackTrace();   // 输出堆栈的异常信息
        Result error = Result.error("操作失败，请联系管理员");
        return error;
    }
}
