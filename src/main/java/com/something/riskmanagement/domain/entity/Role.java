package com.something.riskmanagement.domain.entity;

import com.something.riskmanagement.common.BaseEntity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by H_Gohargazi
 * on 9/5/2023
 */

@Entity
@Table(name = "ROLE", schema = "apps",
        indexes = {@Index(name = "ROLE_IDX", columnList = "ROLEGRP_ID")})

public class Role extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "IS_ACTIVE")
    private Boolean isActive = true;

    @Column(name = "PASS_CHANGE_PERIOD")
    private Integer passwordChangePeriod;

    @ManyToMany
    @JoinTable(name = "ROLE_ENTITYAUTHORITIES",
            joinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "ENTITY_ID", referencedColumnName = "ID")},
            indexes = {
                    @Index(name = "ROL_ENTITY_IDX_1", columnList = "ROLE_ID"),
                    @Index(name = "ROL_ENTITY_IDX_2", columnList = "ENTITY_ID")
            }
    )
    private List<EntityAuthority> entityAuthorities;

    @ManyToMany
    @JoinTable(name = "ROLE_SERVICEAUTHORITIES",
            joinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "SRVC_ID", referencedColumnName = "ID"),
            indexes = {
                    @Index(name = "ROL_SRVC_IDX_1", columnList = "ROLE_ID"),
                    @Index(name = "ROL_SRVC_IDX_2", columnList = "SRVC_ID")
            }
    )
    private List<ServiceAuthority> serviceAuthorities;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public List<EntityAuthority> getEntityAuthorities() {
        return entityAuthorities;
    }

    public void setEntityAuthorities(List<EntityAuthority> entityAuthorities) {
        this.entityAuthorities = entityAuthorities;
    }

    public List<ServiceAuthority> getServiceAuthorities() {
        return serviceAuthorities;
    }

    public void setServiceAuthorities(List<ServiceAuthority> serviceAuthorities) {
        this.serviceAuthorities = serviceAuthorities;
    }
}
