package com.something.riskmanagement.domain.enumeration;

import com.something.riskmanagement.common.BaseEnum;

/**
 * Created by H_Gohargazi
 * on 9/5/2023
 */

public enum ValueType implements BaseEnum {
    INT_NUMBER("ValueType.IntNumber"),
    FLOAT_NUMBER("ValueType.FloatNumber"),
    AMOUNT("ValueType.Amount"),
    STRING("ValueType.String"),
    DATE("ValueType.Date"),
    LIST_INT_NUMBER("ValueType.IntNumber"),
    LIST_FLOAT_NUMBER("ValueType.FloatNumber"),
    LIST_AMOUNT("ValueType.Amount"),
    LIST_STRING("ValueType.String"),
    LIST_DATE("ValueType.Date");

    private final String value;

    ValueType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

