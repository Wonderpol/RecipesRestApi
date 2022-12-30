package com.example.recipesapi.v1.security.controller;

import com.example.recipesapi.v1.security.model.dto.UserDto;
import com.example.recipesapi.v1.security.model.entity.User;
import com.example.recipesapi.v1.security.model.request.AuthenticationRequest;
import com.example.recipesapi.v1.security.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    public final UserService userService;

    public AuthenticationController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody AuthenticationRequest authenticationRequest) {
        return new ResponseEntity<>(userService.registerUser(authenticationRequest), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public User getUser(@PathVariable Long id) {
        final User userById = userService.getUserById(id);
        return userById;
    }

    @GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable final String email) {
        final User user = userService.getUserByEmail(email);
        return user;
    }

}
