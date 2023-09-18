package com.something.riskmanagement.domain.entity.baseinfo;

import com.something.riskmanagement.common.BaseEntity;

import javax.persistence.*;

/**
 * Created by H_Gohargazi
 * on 9/18/2023
 */

@Entity
@Table(name = "RISK_TRUSTEE", schema = "apps")
public class RiskTrustee extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CODE")
    private String code;

    @Column(name = "DESC")
    private String desc;

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
}