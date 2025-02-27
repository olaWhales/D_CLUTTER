package com.example.D_CLUTTER.data;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
//    @Component
    public class UserPrincipal implements UserDetails {

        private final Users users;
        public UserPrincipal(Users users ) {
            this.users = users;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        }

        @Override
        public String getPassword() {
            return users.getPassword();
        }

        @Override
        public String getUsername() {
            return users.getEmail();
        }

        @Override
        public boolean isAccountNonExpired() {
//        return UserDetails.super.isAccountNonExpired();
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
//        return UserDetails.super.isAccountNonLocked();
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
//        return UserDetails.super.isCredentialsNonExpired();
            return true;
        }

        @Override
        public boolean isEnabled() {
//        return UserDetails.super.isEnabled();
            return true;
        }

        public Users getUsers() {
            return users;
        }
    }

