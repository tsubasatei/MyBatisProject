package com.xt.mybatis.exception;

/**
 * Created with xt.
 * Date: 2018/5/3
 * Time: 17:07
 * Description: 系统自定义异常类，针对预期的异常，需要在程序中抛出此类异常
 */
public class CustomerException extends Exception {

    private String message;

    public CustomerException(String message){
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
