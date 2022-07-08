package com.example.movie.model;

import lombok.Data;

@Data
public class RegisterModel {
    private String username;
    
    private String password;
    
    private String email;
    
    private String phone;
    
    private String role;
}
