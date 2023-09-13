package com.something.riskmanagement.service.business;

import com.something.riskmanagement.api.facade.ReportFacade;
import com.something.riskmanagement.api.model.FileType;
import com.something.riskmanagement.api.model.ReportModel;
import com.something.riskmanagement.api.model.ReportRequest;
import com.something.riskmanagement.api.model.ReportResponse;
import com.something.riskmanagement.common.config.ServiceProperties;
import com.something.riskmanagement.common.exception.BaseException;
import com.something.riskmanagement.common.util.calendar.DateUtil;
import com.something.riskmanagement.domain.Keyword;
import com.something.riskmanagement.domain.entity.Report;
import com.something.riskmanagement.domain.entity.ReportDetail;
import com.something.riskmanagement.domain.enumeration.ReportType;
import com.something.riskmanagement.service.dao.ReportDao;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.sql.DataSource;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by H_Gohargazi
 * on 9/5/2023
 */

@Service
public class ReportService implements ReportFacade {
    private static final Logger LOG = LoggerFactory.getLogger(ReportService.class);
    private final ReportDao reportRepository;
    private final ServiceProperties props;

    @Resource(name = "dataSource")
    private DataSource dataSource;

    @Autowired
    public ReportService(ReportDao reportRepository, ServiceProperties props) {
        this.reportRepository = reportRepository;
        this.props = props;
    }

    @Override
    public List<ReportModel> getAllReports() {
        LOG.info("START : getAllReports service method");
        List<Long> idList = reportRepository.findAllIds();
        List<Report> reportList = new ArrayList<>();
        for (Long id : idList)
            reportList.add(reportRepository.getOne(id));
        LOG.info("END : getAllReports service method");
        return null;
    }

    @Override
    public void uploadReport(Report report) throws BaseException {
        LOG.info("START : uploadReport service method");
        checkReportFormat(report);
        reportRepository.save(report);
        LOG.info("END : uploadReport service method");
    }

    @Override
    public void updateReport(Report report) throws BaseException {
        LOG.info("START : updateReport service method");
        checkReportExistence(report);
        checkReportFormat(report);
        reportRepository.save(report);
        LOG.info("END : updateReport service method");
    }

    private void checkReportExistence(Report report) throws BaseException {
        if (report == null)
            throw new BaseException("mnt.exception.report.not.exist");
    }

    private void checkReportFormat(Report report) throws BaseException {
        try {
            InputStream targetStream = new ByteArrayInputStream(report.getContent().getBytes(Keyword.JRSTYLE_ENCODING));
            JRXmlLoader.load(targetStream);
        } catch (JRException | UnsupportedEncodingException e) {
            throw new BaseException("mnt.exception.report.format", e);
        }
    }

    private JasperDesign checkReportFormat(InputStream targetStream) throws BaseException {
        try {
            return JRXmlLoader.load(targetStream);
        } catch (JRException e) {
            throw new BaseException("mnt.exception.report.format", e);
        }
    }

    @Override
    public ReportResponse createReport(ReportRequest reportRequest) throws BaseException {
        ReportResponse reportResponse = new ReportResponse();
        byte[] output = new byte[0];
        LOG.info("START : createReport service method");
        Report report = null;
        Map<String, Object> parameters = new HashMap<>();
        if (reportRequest.getReportId() != null)
            report = reportRepository.getOne(reportRequest.getReportId());
        Connection connection = null;
        try {
            InputStream targetStream = null;
            if (report != null) {
                targetStream = new ByteArrayInputStream(report.getContent().getBytes(Keyword.JRSTYLE_ENCODING));
                if (report.getReportDetailList() != null && !report.getReportDetailList().isEmpty()) {
                    createSubReportCompilation(report.getReportDetailList().get(0));
                    parameters.put(Keyword.JR_SUB_REPORT, "./".concat(Keyword.JR_SUB_REPORT_NAME));
                }

            } else {
                if (reportRequest.getType().equals(ReportType.PERIODIC.name())) {
                    if (TimeUnit.MILLISECONDS.toDays(reportRequest.getToDate().getTime() - reportRequest.getFromDate().getTime()) % 365 > props.getReportDuration())
                        throw new BaseException("mnt.exception.report.periodic.duration.limit");
                    targetStream = this.getClass().getResourceAsStream("/reports/rpt_shb_rej_prdc.jrxml");

                } else {
                    targetStream = this.getClass().getResourceAsStream("/reports/rpt_shb_rej_dly.jrxml");
                }
            }
            JasperDesign design = checkReportFormat(targetStream);
            design.addStyle(setStyle());
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            BufferedImage image = ImageIO.read(getClass().getResource("/images/Logo.png"));
            Date currentDate = props.getCurrentDate();
            String createDate = DateUtil.gregorianToPersianLatinNumber(currentDate, DateUtil.SIMPLE_DATE_FORMAT);
            if (reportRequest.getType().equals(ReportType.PERIODIC.name())) {
                dateValidation(reportRequest.getFromDate(), reportRequest.getToDate(), currentDate);
                parameters.put(Keyword.FROM_DATE, DateUtil.gregorianToPersianLatinNumber(reportRequest.getFromDate(), DateUtil.SIMPLE_DATE_FORMAT));
                parameters.put(Keyword.TO_DATE, DateUtil.gregorianToPersianLatinNumber(reportRequest.getToDate(), DateUtil.SIMPLE_DATE_FORMAT));
            }
            parameters.put(Keyword.CREATE_DATE, createDate);
            parameters.put("logo", image);
            String bicListString = "";
//            if (reportRequest.getBicList().size() < props.getBanks().size())
//                for (String bic : reportRequest.getBicList()) {
//                    if (!bicListString.equals(""))
//                        bicListString += ",";
//                    bicListString += "'" + bic + "'";
//                }
            parameters.put(Keyword.BIC_LIST, bicListString);
            connection = dataSource.getConnection();
            JasperPrint jasperPrint = null;
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
            if (reportRequest.getFileType() != null) {
                if (reportRequest.getFileType().equals(FileType.XLS)) {
                    JRXlsExporter c = new JRXlsExporter();
                    c.setConfiguration(configXls());
                    c.setExporterInput(new SimpleExporterInput(jasperPrint));
                    ByteArrayOutputStream ostream = new ByteArrayOutputStream();
                    c.setExporterOutput(new SimpleOutputStreamExporterOutput(ostream));
                    c.exportReport();
                    ostream.close();
                    output = ostream.toByteArray();
                }
                if (reportRequest.getFileType().equals(FileType.XLSX)) {
                    JRXlsxExporter c = new JRXlsxExporter();
                    c.setConfiguration(configXlsx());
                    c.setExporterInput(new SimpleExporterInput(jasperPrint));
                    ByteArrayOutputStream ostream = new ByteArrayOutputStream();
                    c.setExporterOutput(new SimpleOutputStreamExporterOutput(ostream));
                    c.exportReport();
                    ostream.close();
                    output = ostream.toByteArray();
                }
                if (reportRequest.getFileType().equals(FileType.PDF))
                    output = JasperExportManager.exportReportToPdf(jasperPrint);
            }
        } catch (JRException e) {
            throw new BaseException("mnt.exception.report.format", e);
        } catch (SQLException | IOException e) {
            throw new BaseException("mnt.exception.report.create", e);
        } finally {
            try {
                if (connection != null)
                    connection.close();
                new File("./".concat(Keyword.JR_SUB_REPORT_NAME)).delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        LOG.info("END : createReport service method");
        reportResponse.setReportExport(output);
        return reportResponse;
    }

    private JRDesignStyle setStyle() {
        JRDesignStyle normalStyle = new JRDesignStyle();
        normalStyle.setName(Keyword.JRSTYLE_NAME);
        normalStyle.setDefault(true);
        normalStyle.setPdfEncoding(Keyword.JRSTYLE_ENCODING);
        normalStyle.setPdfEmbedded(Boolean.TRUE);
        normalStyle.setBlankWhenNull(Boolean.TRUE);
        return normalStyle;
    }

    private void dateValidation(Date fromDate, Date toDate, Date currentDate) throws BaseException {
        if (fromDate.after(currentDate) ||
                fromDate.equals(currentDate) ||
                toDate.after(currentDate) ||
                toDate.equals(currentDate))
            throw new BaseException("mnt.exception.report.date.duration");
        if (fromDate.after(toDate))
            throw new BaseException("mnt.exception.report.date");
    }

    private SimpleXlsReportConfiguration configXls() {
        SimpleXlsReportConfiguration simpleXlsReportConfiguration = new SimpleXlsReportConfiguration();
        simpleXlsReportConfiguration.setRemoveEmptySpaceBetweenColumns(true);
        simpleXlsReportConfiguration.setRemoveEmptySpaceBetweenRows(true);
        simpleXlsReportConfiguration.setWrapText(true);
        simpleXlsReportConfiguration.setWhitePageBackground(false);
        simpleXlsReportConfiguration.setIgnoreCellBackground(false);
        simpleXlsReportConfiguration.setIgnoreGraphics(true);
        simpleXlsReportConfiguration.setDetectCellType(true);
        simpleXlsReportConfiguration.setCollapseRowSpan(false);
        simpleXlsReportConfiguration.setIgnorePageMargins(true);
        simpleXlsReportConfiguration.setOnePagePerSheet(false);
        simpleXlsReportConfiguration.setColumnWidthRatio(1.10f);
        return simpleXlsReportConfiguration;
    }

    private SimpleXlsxReportConfiguration configXlsx() {
        SimpleXlsxReportConfiguration simpleXlsReportConfiguration = new SimpleXlsxReportConfiguration();
        simpleXlsReportConfiguration.setRemoveEmptySpaceBetweenColumns(true);
        simpleXlsReportConfiguration.setRemoveEmptySpaceBetweenRows(true);
        simpleXlsReportConfiguration.setWrapText(true);
        simpleXlsReportConfiguration.setWhitePageBackground(false);
        simpleXlsReportConfiguration.setIgnoreCellBackground(false);
        simpleXlsReportConfiguration.setIgnoreGraphics(true);
        simpleXlsReportConfiguration.setDetectCellType(true);
        simpleXlsReportConfiguration.setCollapseRowSpan(false);
        simpleXlsReportConfiguration.setIgnorePageMargins(true);
        simpleXlsReportConfiguration.setOnePagePerSheet(false);
        simpleXlsReportConfiguration.setColumnWidthRatio(1.10f);
        return simpleXlsReportConfiguration;
    }

    private void createSubReportCompilation(ReportDetail inputSubReport) throws BaseException {
        LOG.info("START : createSubReport Jasper Compilation method");
        try {
            InputStream targetStream = null;
            if (inputSubReport != null)
                targetStream = new ByteArrayInputStream(inputSubReport.getContent().getBytes(Keyword.JRSTYLE_ENCODING));
            JasperDesign design = checkReportFormat(targetStream);
            JRDesignStyle normalStyle = new JRDesignStyle();
            normalStyle.setName(Keyword.JRSTYLE_NAME);
            normalStyle.setDefault(true);
            normalStyle.setPdfEncoding(Keyword.JRSTYLE_ENCODING);
            normalStyle.setPdfEmbedded(Boolean.TRUE);
            normalStyle.setBlankWhenNull(Boolean.TRUE);
            design.addStyle(normalStyle);
            JasperCompileManager.compileReportToFile(design, "./".concat(Keyword.JR_SUB_REPORT_NAME));
        } catch (JRException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteReport(Long id) throws BaseException {
        LOG.info("START : deleteReport service method");
        Report report = reportRepository.getOne(id);
        checkReportExistence(report);
        reportRepository.delete(report);
        LOG.info("END : deleteReport service method");
    }

}
