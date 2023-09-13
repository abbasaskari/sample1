package com.something.riskmanagement.api.model;

/**
 * Created by H_Gohargazi
 * on 9/5/2023
 */

public class ResponseModel<T> {

    private ResponseType type;
    private T data;
    private String message;

    public ResponseModel() {
    }

    public ResponseModel(ResponseType type, T data, String message) {
        this.type = type;
        this.data = data;
        this.message = message;
    }

    public ResponseType getType() {
        return type;
    }

    public void setType(ResponseType type) {
        this.type = type;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
