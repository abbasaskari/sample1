package com.something.riskmanagement.service;

import com.something.riskmanagement.common.config.ServiceProperties;
import com.something.riskmanagement.common.exception.BaseException;
import com.something.riskmanagement.common.util.LogUtil;
import com.something.riskmanagement.common.util.calendar.DateUtil;
import com.something.riskmanagement.domain.entity.ServiceConfig;
import com.something.riskmanagement.service.dao.ServiceConfigDao;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by h_gohargazi
 * on 9/9/2023
 */

@Service
public class ApplicationStartup {
    private static final Logger LOG = LogUtil.getDefaultLogger(ApplicationStartup.class);

    private final ServiceConfigDao configDao;
    private final ServiceProperties props;

    @Autowired
    public ApplicationStartup(ServiceConfigDao configDao, ServiceProperties props) {
        this.configDao = configDao;
        this.props = props;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void startup() throws BaseException {
        LOG.info("Application is being started ...");

        List<ServiceConfig> configs = configDao.findAll();
        configs.forEach(conf -> {
            Object value = switch (conf.getType()) {
                case INT_NUMBER -> Long.valueOf(conf.getValue());
                case FLOAT_NUMBER -> Double.valueOf(conf.getValue());
                case AMOUNT -> new BigDecimal(conf.getValue());
                case DATE -> DateUtil.stringToGregorian(conf.getValue(), DateUtil.SIMPLE_DATE_FORMAT);
                case LIST_INT_NUMBER -> Arrays.stream(conf.getValue().split(",")).map(v -> Long.valueOf(v.trim())).collect(Collectors.toList());
                case LIST_FLOAT_NUMBER -> Arrays.stream(conf.getValue().split(",")).map(v -> Double.valueOf(v.trim())).collect(Collectors.toList());
                case LIST_AMOUNT -> Arrays.stream(conf.getValue().split(",")).map(v -> new BigDecimal(v.trim())).collect(Collectors.toList());;
                case LIST_DATE -> Arrays.stream(conf.getValue().split(",")).map(v -> DateUtil.stringToGregorian(v.trim(), DateUtil.SIMPLE_DATE_FORMAT)).collect(Collectors.toList());;
                case LIST_STRING -> Arrays.stream(conf.getValue().split(",")).map(String::trim).collect(Collectors.toList());;
                default -> conf.getValue();
            };
            props.put(conf.getKey(), value);
        });

        LOG.info("Application is ready.");
    }

    @PreDestroy
    public void shutdown() throws BaseException {
        LOG.info("Application is being shutdown ...");
    }
}
