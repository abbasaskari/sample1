package com.something.riskmanagement.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by H_Gohargazi
 * on 9/5/2023
 */

public class NameValidator {

    private Pattern pattern;
    private static final String NAME_PATTERN = "([\u0600-\u065F\u066E-\u06D5 ]+)";


    public NameValidator() {
        pattern = Pattern.compile(NAME_PATTERN);
    }

    public boolean validate(final String name) {
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }
}
