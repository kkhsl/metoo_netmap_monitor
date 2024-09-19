package com.metoo.monitor.core.exception;

/**
 * 业务异常
 * @author zzy
 * @version 1.0
 * @date 2024/09/17 10:06
 */
public class BusiException extends RuntimeException{

    private static final long serialVersionUID = -1;

    public BusiException() {
    }

    public BusiException(String msg) {
        super(msg);
    }
    public BusiException(String msg, Exception e) {
        super(msg,e);
    }
}
