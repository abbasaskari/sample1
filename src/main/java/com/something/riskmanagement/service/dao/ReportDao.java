package com.something.riskmanagement.service.dao;

import com.something.riskmanagement.domain.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Mina Navran
 * on 15/9/2020
 */

@Repository
public interface ReportDao extends JpaRepository<Report, Long> {

    @Query("select distinct r.id from Report r ")
    List<Long> findAllIds();
}
