package com.example.D_CLUTTER.service;

import com.example.D_CLUTTER.data.Users;
import com.example.D_CLUTTER.dto.Response.users.UserChangePasswordResponse;
import com.example.D_CLUTTER.dto.Response.users.UserLoginResponse;
import com.example.D_CLUTTER.dto.Response.users.UserRegisterResponse;
import com.example.D_CLUTTER.dto.request.users.UserChangePasswordRequest;
import com.example.D_CLUTTER.dto.request.users.UserLoginRequest;
import com.example.D_CLUTTER.dto.request.users.UserRegisterRequest;
import com.example.D_CLUTTER.repository.UserRepository;
import com.example.D_CLUTTER.securityConfig.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserRegisterServiceImp implements UserRegisterService {
    @Autowired
    private UserRepository userRepository;

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private JwtService jwtService;


    @Override
    public UserRegisterResponse registerUser(UserRegisterRequest userRegisterRequest) {
        Users user = new Users();
        Optional<Users> userOptional = userRepository.findByEmail(userRegisterRequest.getEmail());
        if(userOptional.isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }
        userRegisterRequest.setFirstName(userRegisterRequest.getFirstName());
        userRegisterRequest.setLastName(userRegisterRequest.getLastName());
        user.setUserName(userRegisterRequest.getFirstName() + " " + userRegisterRequest.getLastName());
        user.setEmail(userRegisterRequest.getEmail());
        user.setContact(userRegisterRequest.getContact());
        user.setPassword(bCryptPasswordEncoder.encode(userRegisterRequest.getPassword()));
        user.setDateCreated(LocalDateTime.now());
        userRepository.save(user);

        UserRegisterResponse userRegisterResponse = new UserRegisterResponse();
        userRegisterResponse.setMessage("Registered Successfully");
        return userRegisterResponse;
    }

    @Override
    public UserLoginResponse loginUser(UserLoginRequest userLoginRequest) {
        String email = userLoginRequest.getEmail();
        String password = userLoginRequest.getPassword();
        if(email.isEmpty() || password.isEmpty()) {
            throw new IllegalArgumentException("Email or password cannot be empty");
        }
        Users user = userRepository.findByEmail(email).
                orElseThrow(() -> new IllegalArgumentException("Email not found"));
        if(!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Your have entered the wrong password");
        }
        String token = jwtService.GenerateToken(email);
        System.out.println("email" + email);

        UserLoginResponse userLoginResponse = new UserLoginResponse();
        userLoginResponse.setToken(token);
        userLoginResponse.setMessage("You have successfully logged in");

        return userLoginResponse;
    }

    @Override
    public UserChangePasswordResponse changepassword(UserChangePasswordRequest userChangePasswordRequest) {
        if (userChangePasswordRequest.getOldPassword().isEmpty()) {
            throw new IllegalArgumentException("Old password cannot be empty");
        }

        //I find user by email instead of password
        Users user = userRepository.findByEmail(userChangePasswordRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User with this email does not exist"));

        if (!bCryptPasswordEncoder.matches(userChangePasswordRequest.getOldPassword(), user.getPassword())) {
            throw new IllegalArgumentException("The old password you provided is incorrect");
        }

        // I check if new and confirm password match
        if (!userChangePasswordRequest.getNewPassword().equals(userChangePasswordRequest.getConfirmPassword())) {
            throw new IllegalArgumentException("New password and confirm password do not match");
        }

        // I encrypt and save the new password
        user.setPassword(bCryptPasswordEncoder.encode(userChangePasswordRequest.getNewPassword()));
        userRepository.save(user);

        // Response message
        UserChangePasswordResponse response = new UserChangePasswordResponse();
        response.setMessage("You have successfully changed your password");
        return response;
    }

}
