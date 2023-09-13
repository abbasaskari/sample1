package com.something.riskmanagement.api.model;

import com.something.riskmanagement.domain.enumeration.ReportType;

import javax.persistence.Lob;
import java.util.List;

/**
 * Created by H_Gohargazi
 * on 9/5/2023
 */

public class ReportModel extends BaseModel {

    private String title;

    @Lob
    private String content;

    @Lob
    private List<String> subContent;

    private List<ReportDetailModel> reportDetailModels;

    private ReportType type;



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

    public List<String> getSubContent() {
        return subContent;
    }

    public void setSubContent(List<String> subContent) {
        this.subContent = subContent;
    }

    public ReportType getType() {
        return type;
    }

    public void setType(ReportType type) {
        this.type = type;
    }

    public List<ReportDetailModel> getReportDetailModels() {
        return reportDetailModels;
    }

    public void setReportDetailModels(List<ReportDetailModel> reportDetailModels) {
        this.reportDetailModels = reportDetailModels;
    }
}
