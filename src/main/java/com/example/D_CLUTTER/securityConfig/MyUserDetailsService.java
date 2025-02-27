package com.example.D_CLUTTER.securityConfig;

import com.example.D_CLUTTER.data.UserPrincipal;
import com.example.D_CLUTTER.data.Users;
import com.example.D_CLUTTER.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users users = userRepository.findByEmail(email).
                orElseThrow(()-> new UsernameNotFoundException("user not found"));
        return new UserPrincipal(users);
    }
}