package com.something.riskmanagement.api.model;

import com.something.riskmanagement.common.BaseEnum;

/**
 * Created by H_Gohargazi
 * on 9/5/2023
 */

public enum ResponseType implements BaseEnum {
    SUCCESS("ResponseType.Success"),
    FAILURE("ResponseType.Failure"),
    SERVER_ERROR("ResponseType.ServerError");

    private final String value;

    ResponseType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
