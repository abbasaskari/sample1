package com.something.riskmanagement.api.model;


import javax.persistence.Lob;

/**
 * Created by H_Gohargazi
 * on 9/5/2023
 */

public class ReportDetailModel extends BaseModel {

    @Lob
    private String content;

    private Long reportId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }
}
