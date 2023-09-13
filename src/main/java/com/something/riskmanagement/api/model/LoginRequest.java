package com.something.riskmanagement.api.model;

import javax.validation.constraints.NotNull;

/**
 * Created by h_gohargazi
 * on 9/5/2023
 */

public class LoginRequest {

    @NotNull(message = "um.error.password.notnull")
    private String password;
    @NotNull(message = "um.error.username.notnull")
    private String username;
    private String captchaId;
    private String captchaCode;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCaptchaId() {
        return captchaId;
    }

    public void setCaptchaId(String captchaId) {
        this.captchaId = captchaId;
    }

    public String getCaptchaCode() {
        return captchaCode;
    }

    public void setCaptchaCode(String captchaCode) {
        this.captchaCode = captchaCode;
    }

    @Override
    public String toString() {
        return "{username:" + username + ",captcha:" + captchaCode + "}";
    }
}
