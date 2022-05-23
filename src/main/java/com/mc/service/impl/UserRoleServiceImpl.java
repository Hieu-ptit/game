package com.mc.service.impl;

import com.repository.UserRoleRepository;
import com.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;
}
