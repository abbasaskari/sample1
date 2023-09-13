package com.something.riskmanagement.domain.entity;



import com.something.riskmanagement.common.BaseEntity;
import com.something.riskmanagement.domain.enumeration.ReportType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by H_Gohargazi
 * on 9/5/2023
 */

@Entity
@Table(name = "REPORT", schema = "FMS_MNT")
public class Report  extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TYPE", length = 50)
    @Enumerated(EnumType.STRING)
    private ReportType type;

    @Column(name = "TITLE")
    private String title;

    @Lob
    @Column(name = "CONTENT")
    private String content;

    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinColumn(name = "REPORT_ID")
    private List<ReportDetail> reportDetailList = new ArrayList<>();

    public List<ReportDetail> getReportDetailList() {
        return reportDetailList;
    }

    public void setReportDetailList(List<ReportDetail> reportDetailList) {
        this.reportDetailList = reportDetailList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReportType getType() {
        return type;
    }

    public void setType(ReportType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
