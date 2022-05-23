package com.mc.model.request;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Accessors(chain = true)
public class UserRequest {

    public static final int PASSWORD_MIN_LENGTH = 4;

    public static final int PASSWORD_MAX_LENGTH = 100;

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String confirmPassword;

    @Email
    @Size(min = 5, max = 254)
    @NotBlank(message = "4041")
    private String email;

    private boolean remember;
}
