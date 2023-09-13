package com.something.riskmanagement.api.facade;

import com.something.riskmanagement.api.model.ReportModel;
import com.something.riskmanagement.api.model.ReportRequest;
import com.something.riskmanagement.api.model.ReportResponse;
import com.something.riskmanagement.common.exception.BaseException;

import java.util.List;

/**
 * Created by H_Gohargazi
 * on 9/5/2023
 */

public interface ReportFacade {
    List<ReportModel> getAllReports();
    void uploadReport(ReportModel reportModel) throws BaseException;
    void updateReport(ReportModel reportModel) throws BaseException;
    ReportResponse createReport(ReportRequest reportRequest) throws  BaseException;
    void deleteReport(Long id) throws BaseException;

}
