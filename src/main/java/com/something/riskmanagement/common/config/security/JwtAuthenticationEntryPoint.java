package com.something.riskmanagement.common.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.something.riskmanagement.common.config.ServiceProperties;
import com.something.riskmanagement.common.util.message.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
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
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final MessageUtil messageUtil;
    private final ServiceProperties props;

    @Autowired
    public JwtAuthenticationEntryPoint(ServiceProperties props) {
        this.messageUtil = new MessageUtil(props.getMessageFileNames());
        this.props = props;
    }

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        String message = messageUtil.getMessage("system.exception.unAuthenticated", null, "", props.getLocale());
//        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, message);
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpServletResponse.setHeader("Content-Type", "application/json; charset=UTF-8");
        final Map<String, Object> body = new HashMap<>();
        body.put("msg", message);
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(httpServletResponse.getOutputStream(), body);
    }
}
