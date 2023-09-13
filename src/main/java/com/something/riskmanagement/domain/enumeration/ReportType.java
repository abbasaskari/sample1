package com.something.riskmanagement.domain.enumeration;

import com.something.riskmanagement.common.BaseEnum;

/**
 * Created by H_Gohargazi
 * on 9/5/2023
 */

public enum ReportType implements BaseEnum {

    DAILY("ReportType.daily"),
    PERIODIC("ReportType.periodic");

    private final String value;

    ReportType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
