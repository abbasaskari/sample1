package com.something.riskmanagement.domain.entity;

import com.something.riskmanagement.common.BaseEntity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by H_Gohargazi
 * on 9/5/2023
 */

@Entity
@Table(name = "SRV_AUTH_CAT", schema = "apps",
        indexes = {
                @Index(name = "SRV_AUTH_CAT_IDX1", columnList = "Application_ID")
        }
)
public class ServiceAuthorityCategory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "TITLE")
    private String title;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "category")
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

    public List<ServiceAuthority> getServiceAuthorities() {
        return serviceAuthorities;
    }

    public void setServiceAuthorities(List<ServiceAuthority> serviceAuthorities) {
        this.serviceAuthorities = serviceAuthorities;
    }
}
