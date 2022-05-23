package com.mc.model.dto;

import com.model.bo.Gender;
import com.model.bo.StatusCommon;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
@Accessors(chain = true)
public class UserResponse {

    private String id;

    private String fullName;

    private StatusCommon statusCommon;

    private String email;

    private Integer age;

    private Gender gender;

    private String address;

    private String identity;

    private String phone;

    private BigDecimal bankAccount;

    private String imageKey;

    private LocalDate birthDay;

    private Set<String> roleNames;

}
