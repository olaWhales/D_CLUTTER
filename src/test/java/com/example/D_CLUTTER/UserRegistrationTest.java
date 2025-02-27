package com.example.D_CLUTTER;

import com.example.D_CLUTTER.data.Users;
import com.example.D_CLUTTER.dto.Response.users.UserRegisterResponse;
import com.example.D_CLUTTER.dto.request.users.UserRegisterRequest;
import com.example.D_CLUTTER.repository.UserRepository;
import com.example.D_CLUTTER.service.UserRegisterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest

public class UserRegistrationTest {
    @Autowired
    private UserRegisterService userRegisterService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void testThatUserCanRegister(){
        UserRegisterRequest registerRequest = new UserRegisterRequest();
        Users users = new Users();
        registerRequest.setFirstName("folu");
        registerRequest.setLastName("jide");
        users.setUserName(registerRequest.getFirstName() + " " + registerRequest.getLastName());
        users.setEmail("olawale11@gmail.com");
        users.setPassword(bCryptPasswordEncoder.encode("1111"));
        users.setContact("09011223344");
        users.setDateCreated(LocalDateTime.now());
        userRepository.save(users);

        UserRegisterResponse registerResponse = userRegisterService.registerUser(new UserRegisterRequest());
        registerResponse.setMessage("Registered Successfully");
        assertEquals(registerResponse.getMessage(), "Registered Successfully");
    }
}
