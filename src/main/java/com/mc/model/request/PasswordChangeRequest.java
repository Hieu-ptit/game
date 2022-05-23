package com.mc.model.request;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PasswordChangeRequest {

    private String currentPassword;

    private String newPassword;

    private String repeatPassword;
}
