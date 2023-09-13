package com.something.riskmanagement.common.util;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by H_Gohargazi
 * on 9/5/2023
 */

public class PasswordValidator {
    private Pattern pattern;

    private static final String PASSWORD_PATTERN = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{12,20})";

    public PasswordValidator() {
        pattern = Pattern.compile(PASSWORD_PATTERN);
    }

    public boolean validate(final String password) {
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
