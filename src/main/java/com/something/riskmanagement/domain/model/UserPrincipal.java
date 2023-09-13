package com.something.riskmanagement.domain.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by h_gohargazi
 * on 9/5/2023
 */

public class UserPrincipal implements UserDetails {

    private String username;
    private String password;
    private String roleName;
    private String desc;
    private String token;
    private Date loginTime;
    private String loginAddress;
    private Date lastActivityTime;
    private Collection<? extends GrantedAuthority> serviceAuthorities;
    private List<String> uiAuthorities;
    private Map<String, List<String>> entityAuthorities;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setServiceAuthorities(List<String> serviceAuthorities) {
        this.serviceAuthorities = serviceAuthorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getLoginAddress() {
        return loginAddress;
    }

    public void setLoginAddress(String loginAddress) {
        this.loginAddress = loginAddress;
    }

    public List<String> getUiAuthorities() {
        return uiAuthorities;
    }

    public void setUiAuthorities(List<String> uiAuthorities) {
        this.uiAuthorities = uiAuthorities;
    }

    public Map<String, List<String>> getEntityAuthorities() {
        return entityAuthorities;
    }

    public void setEntityAuthorities(Map<String, List<String>> entityAuthorities) {
        this.entityAuthorities = entityAuthorities;
    }

    public Date getLastActivityTime() {
        return lastActivityTime;
    }

    public void setLastActivityTime(Date lastActivityTime) {
        this.lastActivityTime = lastActivityTime;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return serviceAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
