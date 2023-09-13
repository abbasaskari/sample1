package com.something.riskmanagement.service.dao;

import com.something.riskmanagement.domain.entity.ServiceConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Mina Navran
 * on 15/9/2020
 */

@Repository
public interface ServiceConfigDao extends JpaRepository<ServiceConfig, Long> {

    List<ServiceConfig> findAll();
}
