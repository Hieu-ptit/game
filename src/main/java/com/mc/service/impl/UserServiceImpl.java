package com.mc.service.impl;

import com.mc.exception.BusinessException;
import com.mc.mapper.UserMapper;
import com.mc.model.dto.AccountAuth;
import com.mc.model.dto.DetailUser;
import com.mc.model.dto.LoginResponse;
import com.mc.model.dto.UserResponse;
import com.mc.model.request.LoginRequest;
import com.mc.service.UserService;
import com.mc.util.ErrorCode;
import com.mc.util.ErrorMessage;
import com.mc.util.Global;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

//    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final TokenProvider tokenProvider;

    private static boolean isPasswordLengthInvalid(String password) {
        return (StringUtils.isEmpty(password) || password.length() < Global.PASSWORD_MIN_LENGTH || password.length() > Global.PASSWORD_MAX_LENGTH);
    }

    @Override
    public LoginResponse authorize(LoginRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            request.getEmail().toLowerCase(Locale.ENGLISH),
            request.getPassword()
        );
        Set<String> roleList = new HashSet<>();
//        Account user = userRepository.findByEmailAndStatusNot(request.getEmail().toLowerCase(Locale.ENGLISH), StatusCommon.DELETE).orElse(null);
//        if (user == null)
//            throw new BusinessException(ErrorCode.USER_NOT_FOUND, ErrorMessage.USER_NOT_FOUND);
//        boolean validatePassword = passwordEncoder.matches(request.getPassword(), user.getPassword());
//        if (!validatePassword)
//            throw new BusinessException(ErrorCode.PASSWORD_INVALID, ErrorMessage.PASS_WORD_INVALID);
//        List<RoleAccount> userRoles = userRoleRepository.findByAccountId(user.getId());
//        if (!CollectionUtils.isEmpty(userRoles))
//            roleList = userRoles.stream().map(RoleAccount::getRoleName).collect(Collectors.toSet());
//        AccountAuth accountAuth = userMapper.mapAccountAuth(user);
//        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String jwt = tokenProvider.createToken(authentication, request.isRememberMe(), null);

//        return new LoginResponse(accountAuth, jwt, roleList);
        return null;
    }

    private DetailUser getToken(String tokenRequest){
        String token = null;
        if (org.springframework.util.StringUtils.hasText(tokenRequest) && tokenRequest.startsWith("Bearer "))
            token = tokenRequest.substring(7);
        return tokenProvider.getClaim(token);
    }
}
