package com.something.riskmanagement.api.controller;

import com.something.riskmanagement.api.facade.UserFacade;
import com.something.riskmanagement.api.model.*;
import com.something.riskmanagement.common.exception.AuthenticationException;
import com.something.riskmanagement.common.exception.BaseException;
import com.something.riskmanagement.common.util.LogUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by H_Gohargazi
 * on 9/5/2023
 */

@RestController
@RequestMapping("/api")
public class UserController {
    private static final Logger LOG = LogUtil.getDefaultLogger(UserController.class);

    private final UserFacade userService;

    @Autowired
    public UserController(UserFacade userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/unsec/user/login")
    public ResponseEntity<ResponseModel<?>> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest servletRequest) throws AuthenticationException {
//        LOG.info("START : login rest method");
        LoginResponse loginResponse = userService.login(loginRequest, servletRequest);
//        loginResponse.setBaseInfo(baseInfoFacade.getBaseInfo());
        ResponseModel<?> responseModel = new ResponseModel<>(ResponseType.SUCCESS, loginResponse, "");
//        LOG.info("END : login rest method");
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/sec/user/logout")
    public ResponseEntity<ResponseModel<?>> logout() {
//        LOG.info("START : logout rest method");
        userService.logout();
        ResponseModel<?> responseModel = new ResponseModel<>(ResponseType.SUCCESS, true, "");
//        LOG.info("END : logout rest method");
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/sec/user/change-password")
    public ResponseEntity<ResponseModel<?>> changePassword(@Valid @RequestBody ChangePasswordRequest req) throws BaseException {
//        LOG.info("START : changePassword rest method");
        userService.changePassword(req);
        ResponseModel<?> responseModel = new ResponseModel<>(ResponseType.SUCCESS, true, "");
//        LOG.info("END : changePassword rest method");
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

}
