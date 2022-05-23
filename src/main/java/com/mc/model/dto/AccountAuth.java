package com.mc.model.dto;

import com.mc.model.bo.StatusCommon;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AccountAuth {

    private String username;

    private String accountId;

    private StatusCommon status;

    private String email;

    private String phone;
}
