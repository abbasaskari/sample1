package com.something.riskmanagement.domain.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.something.riskmanagement.api.model.BaseModel;
import com.something.riskmanagement.common.serializer.ByteArrayDeserializer;
import com.something.riskmanagement.common.serializer.ByteArraySerializer;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * Created by H_Gohargazi
 * on 9/5/2023
 */

public class UserProfileModel extends BaseModel {

    @NotEmpty(message = "constraint.user-profile-model.first-name.not-empty")
    private String firstName;
    @NotEmpty(message = "constraint.user-profile-model.last-name.not-empty")
    private String lastName;
    @NotEmpty(message = "constraint.user-profile-model.email.not-empty")
    @Email(message = "constraint.user-profile-model.email.email")
    private String email;
    private byte[] avatar;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonDeserialize(using = ByteArrayDeserializer.class)
    public byte[] getAvatar() {
        return avatar;
    }

    @JsonSerialize(using = ByteArraySerializer.class)
    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }
}
