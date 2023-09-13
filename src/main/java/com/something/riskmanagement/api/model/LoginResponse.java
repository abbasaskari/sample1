package com.something.riskmanagement.api.model;

import java.util.List;
import java.util.Map;

/**
 * Created by h_gohargazi
 * on 9/5/2023
 */

public class LoginResponse {
    private String username;
    private String token;
    private Long currentDate;
    private Map<?,?> locale;
    private List<?> authorities;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Long currentDate) {
        this.currentDate = currentDate;
    }

    public Map<?, ?> getLocale() {
        return locale;
    }

    public void setLocale(Map<?, ?> locale) {
        this.locale = locale;
    }

    public List<?> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<?> authorities) {
        this.authorities = authorities;
    }
}
