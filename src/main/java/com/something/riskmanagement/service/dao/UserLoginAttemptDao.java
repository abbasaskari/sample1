package com.something.riskmanagement.service.dao;

import com.something.riskmanagement.domain.entity.UserLoginAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by H_Gohargazi
 * on 9/5/2023
 */

@Repository
public interface UserLoginAttemptDao extends JpaRepository<UserLoginAttempt,Long>{

}
