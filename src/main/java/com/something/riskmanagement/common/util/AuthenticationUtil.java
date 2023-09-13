package com.something.riskmanagement.common.util;

import com.something.riskmanagement.common.exception.BaseException;
import com.something.riskmanagement.domain.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Kourosh Mozafari
 * on 04/19/2019
 */

public class AuthenticationUtil {

    public static boolean isActive(User user) {
        return user.getActive()
                && user.getRole().getActive();
    }

    public static boolean isPasswordExpired(User user) {
        Integer passwordChangePeriod = user.getPasswordChangePeriod();
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(user.getPasswordChangeDate());
        calendar.add(Calendar.DATE, passwordChangePeriod);
        return calendar.getTime().before(new Date());
    }

    public static void checkPasswordFormatValidation(String password) throws BaseException {
        if (password != null && !new PasswordValidator().validate(password)) {
            throw new BaseException("password.error.invalid-format");
        }
    }

    public static void checkNameFormatValidation(String name) throws BaseException {
        if (name != null && !new NameValidator().validate(name)) {
            throw new BaseException("Name.error.invalid-format");
        }
    }

    public static void checkPasswordDuplicate(String currentPassword, String newPassword) throws BaseException {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (encoder.matches(newPassword, currentPassword))
            throw new BaseException("password.error.same-as-previous");
    }
}

