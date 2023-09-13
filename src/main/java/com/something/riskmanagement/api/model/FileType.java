package com.something.riskmanagement.api.model;


import com.something.riskmanagement.common.BaseEnum;

/**
 * Created by H_Gohargazi
 * on 9/5/2023
 */

public enum FileType implements BaseEnum {
    PDF("FileType.pdf"),
    XLS("FileType.xls"),
    XLSX("FileType.xlsx");

    private final String value;

    FileType(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
