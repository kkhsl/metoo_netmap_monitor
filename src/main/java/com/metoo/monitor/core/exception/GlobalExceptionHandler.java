package com.metoo.monitor.core.exception;

import com.metoo.monitor.core.utils.ResponseUtil;
import com.metoo.monitor.core.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.naming.SizeLimitExceededException;

/**
 * @author HKK
 * @version 1.0
 * @date 2024-06-22 10:39
 */
@Slf4j
//@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(Exception.class)
//    public ModelAndView handleException(Exception ex) {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("error"); // 设置错误页面名称，例如 error.html
//        modelAndView.addObject("errorMessage", ex.getMessage()); // 可以添加自定义错误消息
//        return modelAndView;
//    }


    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        log.error(e.getMessage(),e);
        return ResponseUtil.error();
    }


    @ExceptionHandler(NumberFormatException.class)
    public Result handleNumberFormatException(NumberFormatException ex) {
        // 处理 NumberFormatException 异常
//        ResponseEntity<String>
//        return ResponseEntity.badRequest().body("Invalid data format: " + ex.getMessage());
        return ResponseUtil.error();
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result handleNumberFormatException(MissingServletRequestParameterException ex) {
//        ResponseEntity<String>
//        return ResponseEntity.badRequest().body("Invalid data format: " + ex.getMessage());
        return ResponseUtil.badArgument();
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result handleNumberFormatException(HttpRequestMethodNotSupportedException ex) {
//        ResponseEntity<String>
//        return ResponseEntity.badRequest().body("Invalid data format: " + ex.getMessage());
        return ResponseUtil.badArgument("不支持的HTTP方法");
    }


    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Result handleNumberFormatException(HttpMediaTypeNotSupportedException ex) {
//        ResponseEntity<String>
//        return ResponseEntity.badRequest().body("Invalid data format: " + ex.getMessage());
        return ResponseUtil.badArgument("不支持的媒体类型");
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Result maxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
//        ResponseEntity<String>
//        return ResponseEntity.badRequest().body("Invalid data format: " + ex.getMessage());
        return ResponseUtil.badArgument("文件超过限制大小");
    }

//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 设置响应状态码
//    public ResponseEntity<String> handleOtherException(Exception ex) {
//        // 自定义返回消息
//        String errorMessage = "发生未知异常: " + ex.getMessage();
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
//    }


}