package com.something.riskmanagement.api.model;

import java.util.List;

/**
 * Created by H_Gohargazi
 * on 9/5/2023
 */

public class ReportResponse {
    private byte[] reportExport;

    List<byte[]> subReport;

    public byte[] getReportExport() {
        return reportExport;
    }

    public void setReportExport(byte[] reportExport) {
        this.reportExport = reportExport;
    }

    public List<byte[]> getSubReport() {
        return subReport;
    }

    public void setSubReport(List<byte[]> subReport) {
        this.subReport = subReport;
    }
}
