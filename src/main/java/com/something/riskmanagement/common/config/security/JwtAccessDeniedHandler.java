package com.something.riskmanagement.common.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.something.riskmanagement.common.config.ServiceProperties;
import com.something.riskmanagement.common.util.message.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by h_gohargazi
 * on 9/5/2023
 */

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final MessageUtil messageUtil;
    private final ServiceProperties props;

    @Autowired
    public JwtAccessDeniedHandler(ServiceProperties props) {
        this.messageUtil = new MessageUtil(props.getMessageFileNames());
        this.props = props;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        String message = messageUtil.getMessage("system.exception.unAuthorized", null, "", props.getLocale());
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setHeader("Content-Type", "application/json; charset=UTF-8");
        final Map<String, Object> body = new HashMap<>();
        body.put("msg", message);
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }
}
