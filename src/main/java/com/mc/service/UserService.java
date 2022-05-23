package com.mc.service;

import com.mc.model.dto.LoginResponse;
import com.mc.model.request.LoginRequest;

public interface UserService {

    LoginResponse authorize(LoginRequest request);

}
