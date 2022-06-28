package com.example.UserManagement.responses;

import com.example.UserManagement.model.User;
import lombok.Data;

@Data
public class LoginResponse {
    private String jwt;
    private User user;

    public LoginResponse(String jwt, User user) {
        this.jwt = jwt;
        this.user = user;
    }
}
