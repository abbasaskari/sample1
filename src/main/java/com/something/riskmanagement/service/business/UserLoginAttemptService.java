package com.something.riskmanagement.service.business;

import com.something.riskmanagement.common.util.LogUtil;
import com.something.riskmanagement.domain.entity.UserLoginAttempt;
import com.something.riskmanagement.service.dao.UserLoginAttemptDao;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by h_gohargazi
 * on 9/9/2023
 */

@Service
public class UserLoginAttemptService {
    private static final Logger LOG = LogUtil.getDefaultLogger(UserLoginAttemptService.class);

    private final UserLoginAttemptDao loginAttemptDao;

    @Autowired
    public UserLoginAttemptService(UserLoginAttemptDao loginAttemptDao) {
        this.loginAttemptDao = loginAttemptDao;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save(UserLoginAttempt loginAttempt) {
        loginAttemptDao.save(loginAttempt);
    }

}
