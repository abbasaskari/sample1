package com.something.riskmanagement.api.model;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Created by H_Gohargazi
 * on 9/5/2023
 */

public  class ReportRequest extends RequestModel {

    private Long reportId;

    @NotNull(message = "mnt.error.fileType.notNull")
    private FileType fileType;

    private String type;

    private Date fromDate;

    private Date toDate;


    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "reportId:" + reportId;
    }
}
