package com.something.riskmanagement.service.dao;

import com.something.riskmanagement.domain.entity.OrganizationUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by H_Gohargazi
 * on 9/29/2023
 */

@Repository
public interface OrganizationUnitDao extends JpaRepository<OrganizationUnit, Long> {
}
