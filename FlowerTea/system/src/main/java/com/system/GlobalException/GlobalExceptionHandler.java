package com.system.GlobalException;

import com.system.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result exceptionHandler(Exception e){
        log.error(String.valueOf(e.getStackTrace()));
        log.error(e.getMessage()); 
        return Result.result(404,e.getMessage(),0,null);
    }
}
