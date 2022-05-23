package com.mc.security;

import com.domain.Account;
import com.domain.RoleAccount;
import com.exception.BusinessException;
import com.model.bo.StatusCommon;
import com.repository.UserRepository;
import com.repository.UserRoleRepository;
import com.util.ErrorCode;
import com.util.ErrorMessage;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    public DomainUserDetailsService(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String email) {
        if (!new EmailValidator().isValid(email, null))
            throw new BusinessException(ErrorCode.EMAIL_IS_INVALID, ErrorMessage.EMAIL_INVALID);
        return userRepository.findByEmailAndStatusNot(email, StatusCommon.DELETE).map(user -> createSpringSecurityUser(email, user)).orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " was not found in the database"));
    }

    private Set<String> getRole(Account account) {
        List<RoleAccount> userRoleList = userRoleRepository.findByAccountId(account.getId());
        if (CollectionUtils.isEmpty(userRoleList))
            throw new BusinessException(ErrorCode.USER_NOT_FOUND, ErrorMessage.USER_NOT_FOUND);
        return userRoleList.stream().map(RoleAccount::getRoleName).collect(Collectors.toSet());
    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(String lowercaseLogin, Account account) {
        if (List.of(StatusCommon.INACTIVE, StatusCommon.DELETE).contains(account.getStatus()))
            throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
        Set<String> roleSet = getRole(account);
        List<GrantedAuthority> grantedAuthorities = roleSet.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(account.getEmail(), account.getPassword(), grantedAuthorities);
    }
}
