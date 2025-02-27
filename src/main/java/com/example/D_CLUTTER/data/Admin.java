package com.example.D_CLUTTER.data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Admin {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id ;

    private String name ;
    private String email ;
    private String password ;
    private String phone ;
    private String address ;
}
