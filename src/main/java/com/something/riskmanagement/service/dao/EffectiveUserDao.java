package com.something.riskmanagement.service.dao;

import com.something.riskmanagement.domain.entity.EffectiveUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by H_Gohargazi
 * on 9/29/2023
 */

@Repository
public interface EffectiveUserDao extends JpaRepository<EffectiveUser, Long> {

    @Query("select eu from EffectiveUser eu where eu.user.id = :userId and eu.role.id = :mainRoleId ")
    EffectiveUser findByUserIaAndMainRoleId(@Param("userId") Long userId, @Param("mainRoleId") Long mainRoleId);
}
