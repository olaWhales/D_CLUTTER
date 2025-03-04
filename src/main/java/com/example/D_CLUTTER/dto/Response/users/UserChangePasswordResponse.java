package com.example.D_CLUTTER.dto.Response.users;

import lombok.Data;

@Data
public class UserChangePasswordResponse {
    public String message ;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
