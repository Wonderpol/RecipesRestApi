package com.example.recipesapi.security.model.request;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String email;
    private String password;
}
