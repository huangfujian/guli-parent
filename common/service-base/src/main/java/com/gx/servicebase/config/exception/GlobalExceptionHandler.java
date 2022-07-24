package com.gx.servicebase.config.exception;

import com.gx.commonutils.ResultEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/15 10:28
 * 统一异常处理类
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class) //异常处理
    @ResponseBody //为了返回json
    public ResultEntity error(Exception e) {
        e.printStackTrace();
        return ResultEntity.error().message("执行了全局异常处理.....");
    }

    @ExceptionHandler(value = ArithmeticException.class)
    @ResponseBody
    public ResultEntity error(ArithmeticException e) {
        e.printStackTrace();
        return ResultEntity.error().message("执行了特定的异常处理");
    }

    @ExceptionHandler(value = GuliException.class)
    @ResponseBody
    public ResultEntity error(GuliException e) {
        return ResultEntity.error().message(e.getMsg()).code(e.getCode());
    }
}
