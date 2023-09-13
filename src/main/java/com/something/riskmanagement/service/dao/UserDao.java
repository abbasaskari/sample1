package com.something.riskmanagement.service.dao;

import com.something.riskmanagement.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by H_Gohargazi
 * on 9/5/2023
 */

@Repository
public interface UserDao extends JpaRepository<User,Long>{
    User findByUsername(String username);
}
