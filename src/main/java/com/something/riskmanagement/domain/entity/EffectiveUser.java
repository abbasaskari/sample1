package com.something.riskmanagement.domain.entity;

import com.something.riskmanagement.common.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by H_Gohargazi
 * on 9/29/2023
 */

@Entity
@Table(name = "USERS", schema = "apps")
public class EffectiveUser extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "ROLE_ID")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "UNIT_ID")
    private OrganizationUnit unit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public OrganizationUnit getUnit() {
        return unit;
    }

    public void setUnit(OrganizationUnit unit) {
        this.unit = unit;
    }
}
