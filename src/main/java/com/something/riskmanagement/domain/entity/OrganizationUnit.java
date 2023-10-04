package com.something.riskmanagement.domain.entity;

import com.something.riskmanagement.common.BaseEntity;
import com.something.riskmanagement.domain.enumeration.OrganizationUnitLevel;

import javax.persistence.*;
import java.util.List;

/**
 * Created by H_Gohargazi
 * on 9/29/2023
 */

@Entity
@Table(name = "ORGAN_UNIT", schema = "apps")
public class OrganizationUnit extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CODE")
    private String code;

    @Column(name = "DESC")
    private String desc;

    @Column(name = "LEVEL", length = 50)
    @Enumerated(EnumType.STRING)
    private OrganizationUnitLevel type;

    @OneToOne
    @JoinColumn(name = "USER_ID")
    private User boss;

    @OneToMany
    @JoinTable(name = "SUB_UNITS",
            joinColumns = @JoinColumn(name = "UNIT_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "SUB_UNIT_ID", referencedColumnName = "ID")
    )
    private List<OrganizationUnit> subUnits;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public OrganizationUnitLevel getType() {
        return type;
    }

    public void setType(OrganizationUnitLevel type) {
        this.type = type;
    }

    public User getBoss() {
        return boss;
    }

    public void setBoss(User boss) {
        this.boss = boss;
    }

    public List<OrganizationUnit> getSubUnits() {
        return subUnits;
    }

    public void setSubUnits(List<OrganizationUnit> subUnits) {
        this.subUnits = subUnits;
    }
}
