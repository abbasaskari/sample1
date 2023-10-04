package com.something.riskmanagement.domain.enumeration;

import com.something.riskmanagement.common.BaseEnum;

/**
 * Created by H_Gohargazi
 * on 9/29/2023
 */

public enum OrganizationUnitLevel implements BaseEnum {

    SENIOR_MANAGEMENT("OrganizationUnitLevel.SeniorManagement"),
    DEPUTY_AREA("OrganizationUnitLevel.DeputyArea"),
    DEPARTMENT("OrganizationUnitLevel.Department"),
    GROUP("OrganizationUnitLevel.Group");

    private final String value;

    OrganizationUnitLevel(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
