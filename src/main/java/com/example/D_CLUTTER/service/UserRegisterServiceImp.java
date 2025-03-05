package com.example.D_CLUTTER.service;

import com.example.D_CLUTTER.data.UserPrincipal;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        String email = userChangePasswordRequest.getEmail();
        String password = userChangePasswordRequest.getOldPassword();
        if (userChangePasswordRequest.getOldPassword().isEmpty() || userChangePasswordRequest.getEmail().isEmpty()) {
            throw new IllegalArgumentException("User email or password cannot be empty");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalArgumentException("User is not authenticated");
        }
//        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
//        if(userPrincipal == null){
//            throw new IllegalArgumentException("UserPrincipal cannot be null");
//        }

        Users user = userRepository.findByEmail(email).
                orElseThrow(() -> new IllegalArgumentException("Email not found"));
        user.setPassword(password);
        user.setPassword(userChangePasswordRequest.getNewPassword());
        user.setPassword(userChangePasswordRequest.getConfirmPassword());

        if(!bCryptPasswordEncoder.matches(password, user.getPassword())){
            throw new IllegalArgumentException("Your have entered the wrong password, please provide the correct password");
        }

        throw new IllegalArgumentException("Passwords do not match");

    }

}
