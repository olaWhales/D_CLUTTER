package com.example.D_CLUTTER.service;

import com.example.D_CLUTTER.dto.Response.users.UserChangePasswordResponse;
import com.example.D_CLUTTER.dto.Response.users.UserLoginResponse;
import com.example.D_CLUTTER.dto.Response.users.UserRegisterResponse;
import com.example.D_CLUTTER.dto.request.users.UserChangePasswordRequest;
import com.example.D_CLUTTER.dto.request.users.UserLoginRequest;
import com.example.D_CLUTTER.dto.request.users.UserRegisterRequest;

public interface UserRegisterService {
    UserRegisterResponse registerUser(UserRegisterRequest userRegisterRequest);
    UserLoginResponse loginUser(UserLoginRequest userLoginRequest);
    UserChangePasswordResponse changepassword(UserChangePasswordRequest userChangePasswordRequest);
}
