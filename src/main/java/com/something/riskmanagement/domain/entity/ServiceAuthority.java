package com.something.riskmanagement.domain.entity;

import com.something.riskmanagement.common.BaseEntity;

import javax.persistence.*;

/**
 * Created by H_Gohargazi
 * on 9/5/2023
 */

@Entity
@Table(name = "SERVICE_AUTHORITY", schema = "apps",
        indexes = {@Index(name = "SRV_AUTHRT_IDX", columnList = "SRV_AUTH_CAT_ID")})
public class ServiceAuthority extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "TITLE")
    private String title;

    @ManyToOne
    @JoinColumn(name = "SRV_AUTH_CAT_ID")
    private ServiceAuthorityCategory category;

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

    public ServiceAuthorityCategory getCategory() {
        return category;
    }

    public void setCategory(ServiceAuthorityCategory category) {
        this.category = category;
    }
}
