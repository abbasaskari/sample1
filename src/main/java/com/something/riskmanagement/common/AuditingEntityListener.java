package com.something.riskmanagement.common;

import com.something.riskmanagement.domain.model.UserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

/**
 * Created by Hojjat Gohargazi
 * on 9/5/2023
 */

public class AuditingEntityListener {

    @PrePersist
    public void onPrePersist(BaseEntity e) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        e.setCreatedBy(user.getUsername());
        e.setCreatedAt(new Date());
    }

    @PreUpdate
    public void onPreUpdate(BaseEntity e) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        e.setUpdatedBy(user.getUsername());
        e.setUpdatedAt(new Date());
    }
}
