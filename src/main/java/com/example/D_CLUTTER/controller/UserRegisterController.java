package com.example.D_CLUTTER.controller;

import com.example.D_CLUTTER.dto.request.users.UserChangePasswordRequest;
import com.example.D_CLUTTER.dto.request.users.UserLoginRequest;
import com.example.D_CLUTTER.dto.request.users.UserRegisterRequest;
import com.example.D_CLUTTER.service.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/User")
public class UserRegisterController {
    @Autowired
    UserRegisterService userRegisterService;

    @PostMapping("/sign_up/")
    public ResponseEntity<?> Register(@RequestBody UserRegisterRequest userRegisterRequest) {
        try{
            return new ResponseEntity<>(userRegisterService.registerUser(userRegisterRequest), HttpStatus.CREATED);
        }catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login/")
    public ResponseEntity<?> Login(@RequestBody UserLoginRequest userLoginRequest) {
        try{
            return new ResponseEntity<>(userRegisterService.loginUser(userLoginRequest), HttpStatus.OK);
        }catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/changePassword/")
    public ResponseEntity<?> ChangePassword(@RequestBody UserChangePasswordRequest userChangePasswordRequest) {
        try{
            return new ResponseEntity<>(userRegisterService.changepassword(userChangePasswordRequest), HttpStatus.OK);
        }catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
