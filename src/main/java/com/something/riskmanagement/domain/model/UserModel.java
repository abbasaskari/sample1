package com.something.riskmanagement.domain.model;

import com.something.riskmanagement.api.model.BaseModel;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by H_Gohargazi
 * on 9/5/2023
 */

public class UserModel extends BaseModel {

    @NotEmpty(message = "constraint.user-model.username.not-empty")
    private String username;
    @NotEmpty(message = "constraint.user-model.password.not-empty")
    @Size(min = 12, message = "constraint.user-model.password.size")
    private String password;
    private UserProfileModel userProfile;
    private Boolean isActive;
    private Boolean changePassword;
    private Date passwordChangeDate;
    private Integer passwordChangePeriod;

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

    public UserProfileModel getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfileModel userProfile) {
        this.userProfile = userProfile;
    }

    public Boolean getChangePassword() {
        return changePassword;
    }

    public void setChangePassword(Boolean changePassword) {
        this.changePassword = changePassword;
    }

    public Date getPasswordChangeDate() {
        return passwordChangeDate;
    }

    public void setPasswordChangeDate(Date passwordChangeDate) {
        this.passwordChangeDate = passwordChangeDate;
    }

    public Integer getPasswordChangePeriod() {
        return passwordChangePeriod;
    }

    public void setPasswordChangePeriod(Integer passwordChangePeriod) {
        this.passwordChangePeriod = passwordChangePeriod;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
