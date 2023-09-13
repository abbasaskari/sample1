package com.something.riskmanagement.domain.entity;

import com.something.riskmanagement.common.BaseEntity;
import com.something.riskmanagement.domain.enumeration.ValueType;

import javax.persistence.*;

/**
 * Created by H_Gohargazi
 * on 9/5/2023
 */

@Entity
@Table(name = "CONFIGS", schema = "apps")
public class ServiceConfig extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "C_KEY")
    private String key;

    @Column(name = "C_VALUE")
    private String value;

    @Column(name = "C_TYPE")
    private ValueType type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ValueType getType() {
        return type;
    }

    public void setType(ValueType type) {
        this.type = type;
    }
}
