package com.example.D_CLUTTER.dto.request.users;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserRegisterRequest {
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String contact;
//    private LocalDateTime dateCreated;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getContact() {
        return contact;
    }

//    public LocalDateTime getDateCreated() {
//        return dateCreated;
//    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

//    public void setDateCreated(LocalDateTime dateCreated) {
//        this.dateCreated = dateCreated;
//    }


}
