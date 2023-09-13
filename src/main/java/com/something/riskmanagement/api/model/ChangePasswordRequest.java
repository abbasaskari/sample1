package com.something.riskmanagement.api.model;

import javax.validation.constraints.NotNull;

/**
 * Created by h_gohargazi
 * on 9/5/2023
 */

public class ChangePasswordRequest {

    @NotNull(message = "um.error.password.notnull")
    private String oldPassword;
    @NotNull(message = "um.error.newPassword.notnull")
    private String newPassword;
    @NotNull(message = "um.error.confirmPassword.notnull")
    private String confirmPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
