package com.example.D_CLUTTER.dto.request.users;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class UserChangePasswordRequest {
    public String oldPassword;
    public String newPassword;
    public String confirmPassword;

}
