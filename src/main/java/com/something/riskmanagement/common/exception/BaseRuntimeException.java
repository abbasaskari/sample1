package com.something.riskmanagement.common.exception;

/**
 * Created by h_gohargazi
 * on 9/5/2023
 */

public class BaseRuntimeException extends RuntimeException {
    private String message;
    private Object[] params;

    public BaseRuntimeException(Throwable cause) {
        super(cause);
    }

    public BaseRuntimeException(String message) {
        super(message);
        this.message = message;
    }

    public BaseRuntimeException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    public BaseRuntimeException(String message, Object[] params) {
        super(message);
        this.message = message;
        this.params = params;
    }

    public BaseRuntimeException(String message, Object[] params, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.params = params;
    }

    public String getMessage() {
        return this.message;
    }

    public Object[] getParams() {
        return this.params;
    }

}