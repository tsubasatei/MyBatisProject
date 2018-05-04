package com.xt.mybatis.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created with xt.
 * Date: 2018/5/3
 * Time: 17:29
 * Description: 全局异常处理器
 */
@ControllerAdvice
public class CustomerExceptionHandler{

    private final static Logger logger = LoggerFactory.getLogger(CustomerExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public ModelAndView handle(Exception ex){

        CustomerException customerException = null;
        if(ex instanceof CustomerException){
            customerException = (CustomerException) ex;
        }else{
            logger.error("【系统异常】{}", ex);
            customerException = new CustomerException("位置错误");
        }
        String message = customerException.getMessage();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", message);
        modelAndView.setViewName("error");
        return modelAndView;
    }

}
