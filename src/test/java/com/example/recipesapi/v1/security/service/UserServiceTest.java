package com.example.recipesapi.v1.security.service;

import com.example.recipesapi.v1.security.exception.UserAlreadyExistsException;
import com.example.recipesapi.v1.security.exception.UserNotFoundException;
import com.example.recipesapi.v1.security.model.entity.User;
import com.example.recipesapi.v1.security.model.request.AuthenticationRequest;
import com.example.recipesapi.v1.security.repository.UserRepository;
import com.example.recipesapi.v1.security.service.UserService;
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

    @Test
    void getUserById_shouldReturnUser() {
        //given
        Long userId = 1L;
        User user = new User("test@test.com", "password");

        given(userRepository.findById(any())).willReturn(Optional.of(user));
        //when
        userServiceUnderTest.getUserById(userId);
        //then

        final ArgumentCaptor<Long> userIdLongArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(userRepository).findById(userIdLongArgumentCaptor.capture());
        final Long capturedValue = userIdLongArgumentCaptor.getValue();
        assertThat(capturedValue).isEqualTo(userId);
    }

    @Test
    void getUserById_shouldThrow_UserNotFoundException() {
        //given
        Long userId = 1L;

        given(userRepository.findById(any())).willReturn(Optional.empty());
        //when
        //then
        assertThatThrownBy(() -> userServiceUnderTest.getUserById(userId))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User with id: " + userId + " not found");

    }

    @Test
    void getUserByEmail_shouldReturnUser() {
        //given
        User user = new User("test@test.com", "password");

        given(userRepository.findByEmail(any())).willReturn(Optional.of(user));
        //when
        userServiceUnderTest.getUserByEmail(user.getEmail());
        //then

        final ArgumentCaptor<String> userEmailLongArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(userRepository).findByEmail(userEmailLongArgumentCaptor.capture());
        final String capturedValue = userEmailLongArgumentCaptor.getValue();
        assertThat(capturedValue).isEqualTo(user.getEmail());
    }

    @Test
    void getUserByEmail_shouldThrow_UserNotFoundException() {
        //given
        String email = "test@test.com";
        given(userRepository.findByEmail(any())).willReturn(Optional.empty());
        //when
        //then
        assertThatThrownBy(() -> userServiceUnderTest.getUserByEmail(email))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User with email: " + email + " does not exists");

    }

}
