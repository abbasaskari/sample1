package com.something.riskmanagement.domain.entity;

import com.something.riskmanagement.common.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by H_Gohargazi
 * on 9/5/2023
 */

@Entity
@Table(name = "USERS", schema = "apps")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @SequenceGenerator(name = "USERS_SEQ", sequenceName = "apps.USERS_SEQ")
    @Column(name = "ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "USER_USERPROFILE",
            joinColumns = @JoinColumn(name = "USERS_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "USR_PRFL_ID", referencedColumnName = "ID"),
            indexes = {
                    @Index(name = "USER_USERPROFILE_IDX_1", columnList = "USERS_ID"),
                    @Index(name = "USER_USERPROFILE_IDX_2", columnList = "USR_PRFL_ID")
            }
    )
    private UserProfile userProfile;

    @Column(name = "IS_ACTIVE")
    private Boolean isActive = true;

    @Column(name = "CHANGE_PASSWORD")
    private Boolean changePassword = true;

    @Column(name = "PASS_CHANGE_DATE", nullable = false)
    private Date passwordChangeDate = new Date();

    @Column(name = "PASS_CHANGE_PERIOD")
    private Integer passwordChangePeriod = 60;

    @ManyToOne
    @JoinTable(name = "ROLE_USER",
            joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")
    )
    private Role mainRole;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserLoginAttempt loginAttempt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getPasswordChangeDate() {
        return passwordChangeDate;
    }

    public void setPasswordChangeDate(Date passwordChangeDate) {
        this.passwordChangeDate = passwordChangeDate;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public Boolean getChangePassword() {
        return changePassword;
    }

    public void setChangePassword(Boolean changePassword) {
        this.changePassword = changePassword;
    }

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

    public Role getMainRole() {
        return mainRole;
    }

    public void setMainRole(Role mainRole) {
        this.mainRole = mainRole;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Integer getPasswordChangePeriod() {
        return passwordChangePeriod;
    }

    public void setPasswordChangePeriod(Integer passwordChangePeriod) {
        this.passwordChangePeriod = passwordChangePeriod;
    }

    public UserLoginAttempt getLoginAttempt() {
        return loginAttempt;
    }

    public void setLoginAttempt(UserLoginAttempt loginAttempt) {
        this.loginAttempt = loginAttempt;
    }
}
