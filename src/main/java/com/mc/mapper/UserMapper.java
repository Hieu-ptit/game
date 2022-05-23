package com.mc.mapper;

import com.domain.Account;
import com.domain.RoleAccount;
import com.model.bo.StatusCommon;
import com.model.dto.AccountAuth;
import com.model.dto.DetailUser;
import com.model.dto.UserResponse;
import com.model.request.UserRequest;
import com.security.AuthoritiesConstants;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.jhipster.security.RandomUtil;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserMapper {

    public Account mapToEntity(Account newUser, UserRequest userRequest) {

        newUser.setStatus(StatusCommon.ACTIVE);
        if (userRequest.getEmail() != null) {
            newUser.setEmail(userRequest.getEmail().toLowerCase());
        }
        newUser.setActivationKey(RandomUtil.generateActivationKey());

        return newUser;
    }

    public UserResponse mapToResponse(Account newUser) {

        return new UserResponse().setId(newUser.getId())
            .setAge(newUser.getAge())
            .setEmail(newUser.getEmail())
            .setAddress(newUser.getAddress())
            .setImageKey(newUser.getImageUrl())
            .setStatusCommon(newUser.getStatus())
            .setFullName(newUser.getFullName())
            .setBirthDay(newUser.getBirthDay())
            .setGender(newUser.getGender());
    }

    public UserResponse mapToResponse(Account newUser, Map<String, List<RoleAccount>> userRoleMap) {

        List<RoleAccount> userRole = userRoleMap.get(newUser.getId());

        Set<String> roleNames = userRole.stream().map(RoleAccount::getRoleName).collect(Collectors.toSet());
        return new UserResponse().setId(newUser.getId())
            .setAge(newUser.getAge())
            .setEmail(newUser.getEmail())
            .setAddress(newUser.getAddress())
            .setStatusCommon(newUser.getStatus())
            .setFullName(newUser.getFullName())
            .setRoleNames(roleNames);
    }

    public DetailUser mapToUserDetail(Account user) {
        return new DetailUser().setUserId(user.getId())
            .setEmail(user.getEmail())
            .setName(user.getFullName());
    }

    public RoleAccount mapToEntityUserRole(String userId) {
        return new RoleAccount().setAccountId(userId).setRoleName(AuthoritiesConstants.USER);
    }

    public AccountAuth mapAccountAuth(Account user) {
        return new AccountAuth().setAccountId(user.getId()).setEmail(user.getEmail())
            .setStatus(user.getStatus()).setUsername(user.getFullName());
    }

    public List<UserResponse> mapToListUserResponse(List<Account> userList) {
        return userList.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<UserResponse> mapToListUserResponse(List<Account> userList, Map<String, List<RoleAccount>> userRoleMap) {
        return userList.stream().map(it -> mapToResponse(it, userRoleMap)).collect(Collectors.toList());
    }

    public DetailUser mapToUser(Claims claims) {
        return new DetailUser().setEmail(claims.getSubject()).setUserId(claims.getId());
    }
}
