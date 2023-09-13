package com.something.riskmanagement.api.facade;

import com.something.riskmanagement.api.model.ChangePasswordRequest;
import com.something.riskmanagement.api.model.LoginRequest;
import com.something.riskmanagement.api.model.LoginResponse;
import com.something.riskmanagement.common.exception.AuthenticationException;
import com.something.riskmanagement.common.exception.BaseException;
import com.something.riskmanagement.domain.model.UserModel;
import com.something.riskmanagement.domain.model.UserPrincipal;
import com.something.riskmanagement.domain.model.UserProfileModel;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by h_gohargazi
 * on 9/5/2023
 */

public interface UserFacade {

    LoginResponse login(LoginRequest loginRequest, HttpServletRequest servletRequest) throws AuthenticationException;

    void logout();

    void changePassword(ChangePasswordRequest req) throws BaseException;

    void updateLogonUser(UserPrincipal user);

    void insertUser(UserModel user);

    void updateUser(UserModel user);

    UserModel getUser(Long userId);

    UserProfileModel getUserProfile();

    void updateUserProfile(UserProfileModel profile);

    void loginSystem();

    void logoutSystem();

}
