package com.something.riskmanagement.common.config.security;

import com.something.riskmanagement.common.config.ServiceProperties;
import com.something.riskmanagement.domain.model.UserPrincipal;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by h_gohargazi
 * on 9/5/2023
 */

@Component
public class JwtTokenProvider {

    private final ServiceProperties config;

    @Autowired
    public JwtTokenProvider(ServiceProperties config) {
        this.config = config;
    }

    public String generateToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Date now = userPrincipal.getLoginTime();
        Date expirationDate = new Date(now.getTime() + config.getJwtExpirationInMs());

        return Jwts.builder()
//                .setSubject(Long.toString(userPrincipal.getId()))
                .setId(String.valueOf(userPrincipal.getLoginTime().getTime()))
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, config.getJwtSecret())
                .compact();
    }

    public String[] getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(config.getJwtSecret())
                .parseClaimsJws(token)
                .getBody();
        return new String[]{claims.getSubject(), claims.getId()};
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(config.getJwtSecret()).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public boolean validateActiveUser(UserPrincipal principal) {
        return principal.getLastActivityTime().getTime() + config.getActivityExpirationInMs() >= new Date().getTime();
    }

}
