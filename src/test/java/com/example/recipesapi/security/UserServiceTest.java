package com.example.recipesapi.security;

import com.example.recipesapi.security.exception.UserAlreadyExistsException;
import com.example.recipesapi.security.model.CustomUserDetails;
import com.example.recipesapi.security.model.entity.User;
import com.example.recipesapi.security.model.request.AuthenticationRequest;
import com.example.recipesapi.security.repository.UserRepository;
import com.example.recipesapi.security.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userServiceUnderTest;

    @Test
    void testRegisterUser_shouldRegister() {
        //given

        AuthenticationRequest authenticationRequest1 = AuthenticationRequest
                .builder()
                .email("test@test.com")
                .password("password")
                .build();

        given(userRepository.findByEmail(anyString())).willReturn(Optional.empty());
        given(passwordEncoder.encode("password")).willReturn("encoded_password");
        //when
        userServiceUnderTest.registerUser(authenticationRequest1);
        //then
        final ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).saveAndFlush(userArgumentCaptor.capture());

        final User capturedValue = userArgumentCaptor.getValue();

        assertThat(authenticationRequest1.getEmail()).isEqualTo(capturedValue.getEmail());
        assertThat(authenticationRequest1.getPassword()).isEqualTo(capturedValue.getPassword());
    }

    @Test
    void testRegisterUser_shouldThrow_userAlreadyExistsException() {
        //given

        AuthenticationRequest authenticationRequest = AuthenticationRequest
                .builder()
                .email("test@test.com")
                .password("password")
                .build();

        User user = new User(authenticationRequest.getEmail(), authenticationRequest.getPassword());

        given(userRepository.findByEmail(anyString())).willReturn(Optional.of(user));
        //when
        //then
        assertThatThrownBy(() -> userServiceUnderTest.registerUser(authenticationRequest))
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessageContaining("User with email: " + authenticationRequest.getEmail() + " already exists");
    }

}
