package com.something.riskmanagement.common.exception;

/**
 * Created by h_gohargazi
 * on 9/5/2023
 */

public class AuthenticationException extends BaseRuntimeException {

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Object[] params) {
        super(message, params);
    }
}
