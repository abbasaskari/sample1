package com.something.riskmanagement.domain.entity;

import com.something.riskmanagement.common.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by H_Gohargazi
 * on 9/5/2023
 */

@Entity
@Table(name = "USER_LOGIN_ATTEMPT", schema = "apps")
public class UserLoginAttempt extends BaseEntity {
    @Id
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "ATTEMPT", nullable = false)
    private Integer attempt = 0;

    @Column(name = "UNLOCK_TIME", nullable = false)
    private LocalDateTime unlockTime = LocalDateTime.now();

    @OneToOne
    @MapsId
    @JoinColumn(name = "USER_ID")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAttempt() {
        return attempt;
    }

    public void setAttempt(Integer attempt) {
        this.attempt = attempt;
    }

    public LocalDateTime getUnlockTime() {
        return unlockTime;
    }

    public void setUnlockTime(LocalDateTime unlockTime) {
        this.unlockTime = unlockTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
