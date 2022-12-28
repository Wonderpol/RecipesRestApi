package com.example.recipesapi.v1.security.authorization;

import com.example.recipesapi.v1.recipe.model.entity.Recipe;
import com.example.recipesapi.v1.security.authorization.CustomMethodSecurityExpressionRoot;
import com.example.recipesapi.v1.security.model.CustomUserDetails;
import com.example.recipesapi.v1.security.model.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CustomMethodSecurityExpressionRootTest {

    @Mock
    private Authentication authentication;
    @InjectMocks
    private CustomMethodSecurityExpressionRoot customMethodSecurityExpressionRootUnderTest;

    private List<Recipe> recipes = new ArrayList<>();
    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .email("test@test.com")
                .password("password")
                .build();

        recipes = List.of(
                new Recipe(1L,
                        "Carroten soup edited",
                        "Delicious tomate soup edited",
                        "soup edited",
                        List.of("Tomaten1", "Peper1", "sól1"),
                        List.of("Tomaten1", "Peper1", "sól1"),
                        LocalDateTime.now(), user),
                new Recipe(10L,
                        "Carroten soup edited",
                        "Delicious tomate soup edited",
                        "soup edited",
                        List.of("Tomaten1", "Peper1", "sól1"),
                        List.of("Tomaten1", "Peper1", "sól1"),
                        LocalDateTime.now(), user)
        );
    }

    @Test
    void isOwner_shouldReturnTrue() {
        //given
        user.setRecipes(recipes);
        given(authentication.getPrincipal()).willReturn(new CustomUserDetails(user));
        //when
        boolean result = customMethodSecurityExpressionRootUnderTest.isOwner(10L);
        //then
        assertTrue(result);
    }

    @Test
    void isOwner_shouldReturnFalse() {
        //given
        user.setRecipes(recipes);
        given(authentication.getPrincipal()).willReturn(new CustomUserDetails(user));
        //when
        boolean result = customMethodSecurityExpressionRootUnderTest.isOwner(100L);
        //then
        assertFalse(result);
    }

}