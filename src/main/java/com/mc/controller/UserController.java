package com.mc.controller;

import com.mc.model.dto.LoginResponse;
import com.mc.model.dto.Response;
import com.mc.model.request.LoginRequest;
import com.mc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/api/user")
@Validated
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/authenticate")
    public Response<LoginResponse> authorize(@Valid @RequestBody LoginRequest request) {
        return Response.ofSucceeded(userService.authorize(request));
    }
}
