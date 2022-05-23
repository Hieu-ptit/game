package com.mc.model.dto;

import com.dslplatform.json.CompiledJson;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

@CompiledJson
@Data
@Accessors(chain = true)
public class LoginResponse {

    private AccountAuth accountAuth;

    private String token;

    private Set<String> role;

    public LoginResponse() {
    }

    public LoginResponse(AccountAuth accountAuth, String token, Set<String> role) {
        this.accountAuth = accountAuth;
        this.token = token;
        this.role = role;
    }
}
