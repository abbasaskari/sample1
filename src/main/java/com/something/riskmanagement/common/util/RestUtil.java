package com.something.riskmanagement.common.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by h_gohargazi
 * on 9/5/2023
 */

public final class RestUtil<T> {
    private static final Logger LOG = LogUtil.getDefaultLogger(RestUtil.class);

    private static final String SUCCESS_RESP = "SUCCESS";
    private static final int TIMEOUT = 7000;

    private final Class<T> aClass;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public RestUtil(Class<T> aClass) {
        this.aClass = aClass;
        Duration t = Duration.ofMillis(TIMEOUT);
        restTemplate = new RestTemplateBuilder().setConnectTimeout(t).setReadTimeout(t).build();
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public RestUtil(Class<T> aClass, Integer timeout) {
        this.aClass = aClass;
        Duration t = timeout == null ? Duration.ofMillis(TIMEOUT) : Duration.ofMillis(timeout);
        restTemplate = new RestTemplateBuilder().setConnectTimeout(t).setReadTimeout(t).build();
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public RestUtil(Class<T> aClass, boolean skipUnknownProps) {
        this.aClass = aClass;
        Duration t = Duration.ofMillis(TIMEOUT);
        restTemplate = new RestTemplateBuilder().setConnectTimeout(t).setReadTimeout(t).build();
        objectMapper = new ObjectMapper();
        if (skipUnknownProps) objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public RestUtil(Class<T> aClass, Integer timeout, boolean skipUnknownProps) {
        this.aClass = aClass;
        Duration t = timeout == null ? Duration.ofMillis(TIMEOUT) : Duration.ofMillis(timeout);
        restTemplate = new RestTemplateBuilder().setConnectTimeout(t).setReadTimeout(t).build();
        objectMapper = new ObjectMapper();
        if (skipUnknownProps) objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public T executeAndGetOne(String command, HttpEntity<?> request, HttpMethod method) {
        try {
            ResponseEntity<T> response = restTemplate.exchange(command, method, request, aClass);
            if (response.getStatusCode().is2xxSuccessful())
                return response.getBody();
        } catch (Throwable e) {
            String log = e instanceof HttpServerErrorException ? ((HttpServerErrorException) e).getResponseBodyAsString() : e.getMessage();
            LOG.error(log, e);
            return null;
        }
        return null;
    }

    public T executeAndGetOneWithResponseModel(String command, HttpEntity<?> request, HttpMethod method) {
        try {
            ResponseEntity<Map> response = restTemplate.exchange(command, method, request, Map.class);
            if (response.getStatusCode().is2xxSuccessful() && SUCCESS_RESP.equals(response.getBody().get("type").toString())) {
                T data;
                if (response.getBody().get("data") == null)
                    data = null;
                else {
                    data = objectMapper.readValue(objectMapper.writeValueAsString(response.getBody().get("data")), aClass);
                }
                return data;
            }
        } catch (Throwable e) {
            String log = e instanceof HttpServerErrorException ? ((HttpServerErrorException) e).getResponseBodyAsString() : e.getMessage();
            LOG.error(log, e);
            return null;
        }
        return null;
    }

    public List<T> executeAndGetList(String command, HttpEntity<?> request, HttpMethod method) {
        try {
            ResponseEntity<List<T>> response = restTemplate.exchange(command, method, request, ParameterizedTypeReference.forType(aClass));
            if (response.getStatusCode().is2xxSuccessful())
                return response.getBody();
        } catch (Throwable e) {
            String log = e instanceof HttpServerErrorException ? ((HttpServerErrorException) e).getResponseBodyAsString() : e.getMessage();
            LOG.error(log, e);
            return new ArrayList<>();
        }
        return new ArrayList<>();
    }

    public List<T> executeAndGetListWithResponseModel(String command, HttpEntity<?> request, HttpMethod method) {
        try {
            ResponseEntity<Map> response = restTemplate.exchange(command, method, request, Map.class);
            if (response.getStatusCode().is2xxSuccessful() && SUCCESS_RESP.equals(response.getBody().get("type").toString())) {
                List<T> data;
                if (GeneralUtil.isNullOrEmpty((List<?>) response.getBody().get("data")))
                    data = new ArrayList<>();
                else {
                    JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, aClass);
                    data = objectMapper.readValue(objectMapper.writeValueAsString(response.getBody().get("data")), type);
                }
                return data;
            }
        } catch (Throwable e) {
            String log = e instanceof HttpServerErrorException ? ((HttpServerErrorException) e).getResponseBodyAsString() : e.getMessage();
            LOG.error(log, e);
            return null;
        }
        return null;
    }
}