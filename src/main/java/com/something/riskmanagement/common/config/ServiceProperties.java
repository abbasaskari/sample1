package com.something.riskmanagement.common.config;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by h_gohargazi
 * on 9/5/2023
 */

@Component
public class ServiceProperties {
    private Map<String, Object> props;

    public void put(String key, Object value){
        props.put(key, value);
    }
    public Locale getLocale() {
        return (Locale) props.get("LOCALE");
    }

    public Map<String, String> getLocalePair() {
        String lang = ((Locale) props.get("LOCALE")).getLanguage();
        return Map.of("lang", lang, "dir", lang.equals("fa") ? "rtl" : "ltr");
    }

    public void setLocale(String locale) {
        props.put("LOCAL", Locale.forLanguageTag(locale));
    }

    public List<String> getMessageFileNames() {
        return (List<String>) props.get("MESSAGE_FILENAMES");
    }

    public void setMessageFileNames(List<String> messageFileNames) {
        props.put("MESSAGE_FILENAMES", messageFileNames);
    }

    public Long getJwtExpirationInMs() {
        return (Long) props.get("JWT_EXPIRATION_IN_MS");
    }

    public void setJwtExpirationInMs(Long jwtExpirationInMs) {
        props.put("JWT_EXPIRATION_IN_MS", jwtExpirationInMs);
    }

    public Long getActivityExpirationInMs() {
        return (Long) props.get("ACTIVITY_EXPIRATION_IN_MS");
    }

    public void setActivityExpirationInMs(Long activityExpirationInMs) {
        props.put("ACTIVITY_EXPIRATION_IN_MS", activityExpirationInMs);
    }

    public String getJwtSecret() {
        return (String) props.get("JWT_SECRET");
    }

    public void setJwtSecret(String jwtSecret) {
        props.put("JWT_SECRET", jwtSecret);
    }

    public List<String> getAllowedOrigins() {
        return (List<String>) props.get("ALLOWED_ORIGINS");
    }

    public void setAllowedOrigins(List<String> allowedOrigins) {
        props.put("ALLOWED_ORIGINS", allowedOrigins);
    }

    public Integer getMaxLoginAttempts() {
        return (Integer) props.get("MAX_LOGIN_ATTEMPTS");
    }

    public void setMaxLoginAttempts(Integer maxLoginAttempts) {
        props.put("MAX_LOGIN_ATTEMPTS", maxLoginAttempts);
    }

    public Integer getAccountLockDurationMinutes() {
        return (Integer) props.get("ACCOUNT_LOCK_DURATION_MINUTES");
    }

    public void setAccountLockDurationMinutes(Integer accountLockDurationMinutes) {
        props.put("ACCOUNT_LOCK_DURATION_MINUTES", accountLockDurationMinutes);
    }

    public Date getCurrentDate() {
        return (Date) props.get("CURRENT_DATE");
    }

    public void seCurrentDate(Date currentDate) {
        props.put("CURRENT_DATE", currentDate);
    }

    enum PropertyName {
        CURRENT_DATE,
        LOCALE,
        MESSAGE_FILENAMES,
        JWT_EXPIRATION_IN_MS,
        ACTIVITY_EXPIRATION_IN_MS,
        JWT_SECRET,
        ALLOWED_ORIGINS,
        MAX_LOGIN_ATTEMPTS,
        ACCOUNT_LOCK_DURATION_MINUTES;
    }
}