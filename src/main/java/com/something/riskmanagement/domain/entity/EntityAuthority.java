package com.something.riskmanagement.domain.entity;

import com.something.riskmanagement.common.BaseEntity;

import javax.persistence.*;

/**
 * Created by H_Gohargazi
 * on 9/5/2023
 */

@Entity
@Table(name = "ENTITY_AUTHORITY", schema = "apps",
        indexes = {@Index(name = "ENT_AUTHRT_IDX", columnList = "APPENTITY_ID")})
public class EntityAuthority extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "TITLE")
    private String title;

    @ManyToOne
    @JoinColumn(name = "APPENTITY_ID")
    private AppEntity entity;

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

    public AppEntity getEntity() {
        return entity;
    }

    public void setEntity(AppEntity entity) {
        this.entity = entity;
    }
}
