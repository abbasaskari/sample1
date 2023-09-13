package com.something.riskmanagement.domain.entity;

import com.something.riskmanagement.common.BaseEntity;

import javax.persistence.*;

/**
 * Created by H_Gohargazi
 * on 9/5/2023
 */

@Entity
@Table(name = "USER_PROFILE", schema = "apps",
        indexes = {@Index(name = "USR_PRFL_IDX", columnList = "LAST_NAME")/*,
                @Index(name = "USR_PRFL_IDX", columnList = "USERS_ID")*/})
public class UserProfile extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "EMAIL")
    private String email;

    @Lob
    @Column(name = "AVATAR")
    private byte[] avatar;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }
}
