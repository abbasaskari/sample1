package com.something.riskmanagement.domain.entity;

import com.something.riskmanagement.common.BaseEntity;

import javax.persistence.*;

/**
 * Created by H_Gohargazi
 * on 9/5/2023
 */

@Entity
@Table(name = "REPORT_DETAIL", schema = "FMS_MNT")
public class ReportDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Lob
    @Column(name = "CONTENT")
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
