package com.example.recipesapi.security.service;

import com.example.recipesapi.security.model.AuthenticationRequest;
import com.example.recipesapi.security.model.entity.User;
import com.example.recipesapi.security.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(final UserRepository userRepository, final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(RuntimeException::new);
    }


    public User registerUser(AuthenticationRequest user) {
        userRepository.findByEmail(user.getEmail())
                .ifPresent(user1 -> {
                    throw new RuntimeException();
                });
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.saveAndFlush(new User(user.getEmail(), user.getPassword()));
    }
}
