package com.something.riskmanagement.common.exception;

/**
 * Created by h_gohargazi
 * on 9/5/2023
 */

public class BaseException extends Exception {

    private String message;
    private Object[] params;

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message) {
        super(message);
        this.message = message;
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    public BaseException(String message, Object[] params) {
        super(message);
        this.message = message;
        this.params = params;
    }

    public BaseException(String message, Object[] params, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.params = params;
    }

    public String getMessage() {
        return message;
    }

    public Object[] getParams() {
        return params;
    }

}
