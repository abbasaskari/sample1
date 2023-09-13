package com.something.riskmanagement.service.business;

import com.something.riskmanagement.api.facade.UserFacade;
import com.something.riskmanagement.api.model.ChangePasswordRequest;
import com.something.riskmanagement.api.model.LoginRequest;
import com.something.riskmanagement.api.model.LoginResponse;
import com.something.riskmanagement.common.config.ServiceProperties;
import com.something.riskmanagement.common.config.security.JwtTokenProvider;
import com.something.riskmanagement.common.exception.AuthenticationException;
import com.something.riskmanagement.common.exception.BaseException;
import com.something.riskmanagement.common.util.AuthenticationUtil;
import com.something.riskmanagement.common.util.GeneralUtil;
import com.something.riskmanagement.common.util.LogUtil;
import com.something.riskmanagement.common.util.message.MessageUtil;
import com.something.riskmanagement.domain.entity.*;
import com.something.riskmanagement.domain.model.UserModel;
import com.something.riskmanagement.domain.model.UserPrincipal;
import com.something.riskmanagement.domain.model.UserProfileModel;
import com.something.riskmanagement.service.dao.UserDao;
import com.something.riskmanagement.service.dao.UserLoginAttemptDao;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by h_gohargazi
 * on 9/5/2023
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class UserService implements UserFacade, UserDetailsService {
    private static final Logger LOG = LogUtil.getDefaultLogger(UserService.class);
    private final int TIMEOUT = 10000;

    private final Map<String, UserPrincipal> userCache;
    private final ServiceProperties props;
    private final MessageUtil messageUtil;
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationProvider authenticationManager;
    private final UserDao userDao;
    private final UserLoginAttemptService loginAttemptService;

    @Autowired
    public UserService(ServiceProperties props, JwtTokenProvider tokenProvider, AuthenticationProvider authenticationManager, UserDao userDao, UserLoginAttemptService loginAttemptService) {
        this.props = props;
        this.messageUtil = new MessageUtil(props.getMessageFileNames());
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.userDao = userDao;
        this.loginAttemptService = loginAttemptService;
        this.userCache = new HashMap<>();
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest, HttpServletRequest servletRequest) throws AuthenticationException {
//        SimpleCaptcha captcha = SimpleCaptcha.load(servletRequest);
//        boolean isHuman = captcha.validate(loginRequest.getCaptchaCode(), loginRequest.getCaptchaId());
//        if (!isHuman)
//            throw new AuthenticationException("um.exception.invalidCaptcha");
        UserPrincipal principal, user;
        String requestOrigin = servletRequest.getHeader("X-Real-IP");
        requestOrigin = GeneralUtil.isNullOrEmpty(requestOrigin) ? servletRequest.getRemoteAddr() : requestOrigin;
        user = userCache.get(loginRequest.getUsername());
        Authentication authenticate;
        if (user != null && user.getPassword().equals(loginRequest.getPassword()) && tokenProvider.validateToken(user.getToken()) && tokenProvider.validateActiveUser(user)) {
            if (user.getLoginAddress().equals(requestOrigin)) {
                principal = user;
                principal.setLastActivityTime(new Date());
                authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        principal,
                        loginRequest.getPassword()));
            } else
                throw new AuthenticationException("um.exception.duplicateUserLogin", new Object[]{loginRequest.getUsername()});
        } else {
            principal = login(loginRequest);
//            userCache.put(principal.getUsername(), principal);
            authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    principal,
                    loginRequest.getPassword()));
            String token = tokenProvider.generateToken(authenticate);
            principal.setLoginTime(new Date());
            principal.setLastActivityTime(new Date());
            principal.setToken(token);
            principal.setLoginAddress(requestOrigin);
        }

        userCache.put(principal.getUsername(), principal);
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        LoginResponse resp = new LoginResponse();
        resp.setUsername(principal.getUsername());
        resp.setToken(principal.getToken());
        resp.setCurrentDate(props.getCurrentDate().getTime());
        resp.setLocale(Map.of("lang", "fa", "dir", "rtl"));  //todo: fix locale
        resp.setAuthorities(principal.getUiAuthorities());
        return resp;
    }

    @Override
    public void logout() {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userCache.remove(principal.getUsername());
    }

    @Override
    public void changePassword(ChangePasswordRequest req) throws BaseException {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user =userDao.findByUsername(principal.getUsername());
        AuthenticationUtil.checkPasswordFormatValidation(req.getNewPassword());
        AuthenticationUtil.checkPasswordDuplicate(user.getPassword(), req.getNewPassword());
        String encodedPassword = new BCryptPasswordEncoder().encode(req.getNewPassword());
        user.setPassword(encodedPassword);
        user.setPasswordChangeDate(new Date());
        user.setChangePassword(false);
        userDao.save(user);
    }

    @Override
    public void updateLogonUser(UserPrincipal user) {
        if (user != null) {
            user.setLastActivityTime(new Date());
            userCache.put(user.getUsername(), user);
        }
    }

    @Override
    public void insertUser(UserModel user) {

    }

    @Override
    public void updateUser(UserModel user) {

    }

    @Override
    public UserModel getUser(Long userId) {
        return null;
    }

    @Override
    public UserProfileModel getUserProfile() {
        return null;
    }

    @Override
    public void updateUserProfile(UserProfileModel profile) {
    }

    @Override
    public void loginSystem() {
        UserPrincipal principal = new UserPrincipal();
        principal.setUsername("SYSTEM");
        principal.setPassword("system");
        Authentication authenticate = new UsernamePasswordAuthenticationToken(principal, null);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
    }

    @Override
    public void logoutSystem() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    private UserPrincipal login(LoginRequest loginRequest) throws AuthenticationException {
        LOG.info("service start login");

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String username = loginRequest.getUsername();
        User user = userDao.findByUsername(username);
        if (user == null) throw new AuthenticationException("exception.user.not-found");
        boolean matches = encoder.matches(loginRequest.getPassword(), user.getPassword());
        if (!matches) {
            failedLoginAttempt(user.getLoginAttempt());
            throw new AuthenticationException("exception.user.not-found");
        } else {
            successLoginAttempt(user.getLoginAttempt());
        }

        List<String> authorities;
        if (user.getChangePassword() || AuthenticationUtil.isPasswordExpired(user))
            authorities = List.of("User.logout", "User.changePassword", "User.getUserProfile");
        else
            authorities = user.getRole().getServiceAuthorities().stream().map(ServiceAuthority::getName).collect(Collectors.toList());

        Map<String, List<String>> entityAuthorityMap = new HashMap<>();
        user.getRole().getEntityAuthorities().forEach(entityAuthority -> {
            AppEntity entity = entityAuthority.getEntity();
            String name = entity.getName();
            entityAuthorityMap.putIfAbsent(name, new ArrayList<>());
            entityAuthorityMap.get(name).add(entityAuthority.getName());
        });

        UserPrincipal userPrincipal = new UserPrincipal();
        userPrincipal.setUsername(username);
        userPrincipal.setPassword(user.getPassword());
        userPrincipal.setRoleName(user.getRole().getName());
        userPrincipal.setDesc(user.getRole().getTitle());
        userPrincipal.setServiceAuthorities(authorities);
        userPrincipal.setUiAuthorities(null);
        userPrincipal.setEntityAuthorities(entityAuthorityMap);

        LOG.info("service end login");

        return userPrincipal;
    }

    private void successLoginAttempt(UserLoginAttempt loginAttempt) throws AuthenticationException {
        if (loginAttempt.getUnlockTime().compareTo(LocalDateTime.now()) < 0) {
            loginAttempt.setAttempt(0);
        } else {
            loginAttempt.setUnlockTime(LocalDateTime.now().plusMinutes(props.getAccountLockDurationMinutes()));
            saveLoginAttempt(loginAttempt);
            throw new AuthenticationException("um-app.user.locked");
        }
    }

    private void failedLoginAttempt(UserLoginAttempt loginAttempt) throws AuthenticationException {
        if (loginAttempt.getAttempt() < props.getMaxLoginAttempts() - 1) {
            loginAttempt.setAttempt(loginAttempt.getAttempt() + 1);
            saveLoginAttempt(loginAttempt);
        } else if (loginAttempt.getAttempt() == (props.getMaxLoginAttempts() - 1)) {
            loginAttempt.setAttempt(loginAttempt.getAttempt() + 1);
            loginAttempt.setUnlockTime(LocalDateTime.now().plusMinutes(props.getAccountLockDurationMinutes()));
            saveLoginAttempt(loginAttempt);
            throw new AuthenticationException("um-app.user.locked");
        } else if (loginAttempt.getUnlockTime().compareTo(LocalDateTime.now()) < 0) {
            loginAttempt.setAttempt(1);
            saveLoginAttempt(loginAttempt);
        } else {
            loginAttempt.setUnlockTime(LocalDateTime.now().plusMinutes(props.getAccountLockDurationMinutes()));
            saveLoginAttempt(loginAttempt);
            throw new AuthenticationException("um-app.user.locked");
        }
    }

    private void saveLoginAttempt(UserLoginAttempt loginAttempt) {
        loginSystem();
        loginAttemptService.save(loginAttempt);
    }

    // UserDetailService implementation
    @Override
    public UserDetails loadUserByUsername(String username) {
        return userCache.get(username);
    }

}
