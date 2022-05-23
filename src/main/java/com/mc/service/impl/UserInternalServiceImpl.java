package com.mc.service.impl;

import com.domain.Account;
import com.exception.BusinessException;
import com.mapper.UserMapper;
import com.model.bo.StatusCommon;
import com.model.dto.DetailUser;
import com.repository.UserRepository;
import com.service.UserInternalService;
import com.util.ErrorCode;
import com.util.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInternalServiceImpl implements UserInternalService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public DetailUser getUserByEmail(String email) {
        Account user = userRepository.findByEmailAndStatusNot(email, StatusCommon.DELETE).orElse(null);
        if (user == null)
            throw new BusinessException(ErrorCode.USER_NOT_FOUND, ErrorMessage.USER_NOT_FOUND);
        return userMapper.mapToUserDetail(user);
    }
}
