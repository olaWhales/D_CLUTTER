package com.example.D_CLUTTER.service;

import com.example.D_CLUTTER.data.Users;
import com.example.D_CLUTTER.dto.Response.users.UserLoginResponse;
import com.example.D_CLUTTER.dto.Response.users.UserRegisterResponse;
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
//        Optional<Users> userOptional = userRepository.findByEmail(email);
//        if(userOptional.isEmpty()) {
//            throw new IllegalArgumentException("The email you entered isn't found");
//        }
        System.out.print("password" +  password);
        System.out.println("this is request username" +  userLoginRequest.getPassword());
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


}
