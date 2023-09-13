package com.something.riskmanagement.api.controller;

import com.something.riskmanagement.api.facade.ReportFacade;
import com.something.riskmanagement.api.model.*;
import com.something.riskmanagement.common.config.ServiceProperties;
import com.something.riskmanagement.common.exception.BaseException;
import com.something.riskmanagement.common.util.message.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by H_Gohargazi
 * on 9/5/2023
 */

@RestController
@RequestMapping("/api/report")
public class ReportController {
    private static final Logger LOG = LoggerFactory.getLogger(ReportController.class);

    private final ReportFacade reportFacade;
    private final MessageUtil messageUtil;
    private final ServiceProperties settingCache;

    public ReportController(ReportFacade reportFacade, MessageUtil messageUtil, ServiceProperties settingCache) {
        this.messageUtil = messageUtil;
        this.reportFacade = reportFacade;
        this.settingCache = settingCache;
    }

    @PreAuthorize("hasAuthority('Report.getAllReports')")
    @RequestMapping(value = "/getAllReports", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel<List<ReportModel>>> getAllReports() {
        LOG.info("START : getAllReports rest method");
        List<ReportModel> reports = reportFacade.getAllReports();
        LOG.info("END : getAllReports rest method");
        return new ResponseEntity<>(new ResponseModel<>(ResponseType.SUCCESS, reports, ""), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('Report.uploadReport')")
    @RequestMapping(value = "/uploadReport", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel<?>> uploadReport(@Valid @RequestBody ReportModel reportModel) throws BaseException {
        LOG.info("START : uploadReport rest method");
        reportFacade.uploadReport(reportModel);
        String message = messageUtil.getMessage("mnt.notify.report.insert", null, settingCache.getLocale());
        LOG.info("END : uploadReport rest method");
        return new ResponseEntity<>(new ResponseModel<>(ResponseType.SUCCESS, "", message), HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('Report.updateReport')")
    @RequestMapping(value = "/updateReport", method = RequestMethod.PUT)
    public ResponseEntity<ResponseModel<?>> updateReport(@Valid @RequestBody ReportModel reportModel) throws BaseException {
        LOG.info("START : updateReport rest method");
        reportFacade.updateReport(reportModel);
        String message = messageUtil.getMessage("mnt.notify.report.update", null, settingCache.getLocale());
        LOG.info("END : updateReport rest method");
        return new ResponseEntity<>(new ResponseModel<>(ResponseType.SUCCESS, null, message), HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('Report.createReport')")
    @RequestMapping(value = "/createReport", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel<ReportResponse>> createReport(@Valid @RequestBody ReportRequest request) throws BaseException {
        LOG.info("START : createReport rest method");
        ReportResponse reportResponse=reportFacade.createReport(request);
        LOG.info("END : createReport rest method");
        return new ResponseEntity<>(new ResponseModel<>(ResponseType.SUCCESS, reportResponse, ""), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('Report.deleteReport')")
    @RequestMapping(value = "/remove/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseModel<?>> deleteReport(@PathVariable("id") Long id) throws BaseException {
        LOG.info("START : deleteReport rest method");
        reportFacade.deleteReport(id);
        String message = messageUtil.getMessage("mnt.notify.report.delete", null, settingCache.getLocale());
        LOG.info("END : deleteReport rest method");
        return new ResponseEntity<>(new ResponseModel<>(ResponseType.SUCCESS, null, message), HttpStatus.OK);
    }


}
