package com.something.riskmanagement.common.config.security;

import com.something.riskmanagement.domain.model.UserPrincipal;
import com.something.riskmanagement.service.business.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by h_gohargazi
 * on 9/5/2023
 */

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserService userServiceImpl;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String jwt = getJwtFromRequest(httpServletRequest);
        try {
            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                String[] user = tokenProvider.getUsernameFromJWT(jwt);
                UserDetails userDetails = userServiceImpl.loadUserByUsername(user[0]);
//                if (userDetails == null || !((UserPrincipal)userDetails).getToken().equals(jwt))
//                    userDetails = userServiceImpl.loadUserByToken(jwt,user[0]);
                if (tokenProvider.validateActiveUser(((UserPrincipal) userDetails))) {
                    Date now = new Date();
                    ((UserPrincipal) userDetails).setLastActivityTime(new Date());
                    userServiceImpl.updateLogonUser(((UserPrincipal) userDetails));
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Throwable ignored) {
        } //when a user does not exist in user cache, but request has a valid token

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    //    @Override
//    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
////        request.getReader().lines().collect(Collectors.joining(System.lineSeparator())) should remove.
//        HttpServletRequest theRequest = httpServletRequest;
//        String jwt = getJwtFromRequest(httpServletRequest);
//        try {
//            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
//                String[] user = tokenProvider.getUsernameFromJWT(jwt);
//                UserDetails userDetails = userServiceImpl.loadUserByUsername(user[0]);
//
//                //three lines above
//                /*UserDetails userDetails = userServiceImpl.getUser("admin", "password");*/
//                // six lines below
//                if (((UserPrincipal) userDetails).getLoginTime().equals(user[1]) && tokenProvider.validateActiveUser(((UserPrincipal) userDetails))) { //if a user login twice, first one is invalid. (probably not used)
//                    Date now = new Date();
//                    ((UserPrincipal) userDetails).setLastActivityTime(now.getTime());
//                    userServiceImpl.updateUser(((UserPrincipal) userDetails));
//                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                    theRequest = new AddUserHeaderRequestWrapper(httpServletRequest, user[0]);
//                }
//            }
//        } catch (Throwable ignored) {
//        } //when a user does not exist in user cache, but request has a valid token
//
//        filterChain.doFilter(theRequest, httpServletResponse);
//    }
//
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
//
//    public class AddUserHeaderRequestWrapper extends HttpServletRequestWrapper {
//        private final String USER_HEADER = "user";
//        private final String username;
//
//        public AddUserHeaderRequestWrapper(HttpServletRequest request, String username) {
//            super(request);
//            this.username = username;
//        }
//
//        @Override
//        public String getHeader(String name) {
//            return USER_HEADER.equals(name) ? username : super.getHeader(name);
//        }
//
//        @Override
//        public Enumeration<String> getHeaderNames() {
//            List<String> names = Collections.list(super.getHeaderNames());
//            names.add(USER_HEADER);
//            return Collections.enumeration(names);
//        }
//
//        @Override
//        public Enumeration<String> getHeaders(String name) {
//            return USER_HEADER.equals(name) ? Collections.enumeration(List.of(username)) : Collections.enumeration(Collections.list(super.getHeaders(name)));
//        }
//    }
}
