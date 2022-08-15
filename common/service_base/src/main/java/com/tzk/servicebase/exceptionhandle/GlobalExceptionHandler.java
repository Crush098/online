package com.tzk.servicebase.exceptionhandle;


import com.tzk.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
@SuppressWarnings({"all"})

//全局异常处理
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    //全局异常
    @ExceptionHandler(Exception.class)
    @ResponseBody  //返回数据
    public R error(Exception e){
        e.printStackTrace();
        return R.ok().message("执行了全局处理异常");
    }

    //特定异常处理(先找特定的异常方法执行 再找全局异常)
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody  //返回数据
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.ok().message("执行了ArithmeticException异常处理");
    }
    //自定义异常
    @ExceptionHandler(CustomException.class)
    @ResponseBody  //返回数据
    public R error(CustomException e){
        e.printStackTrace();
        return R.ok().message("执行了CustomException异常处理");
    }
}
