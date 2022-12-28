package com.example.recipesapi.v1.recipe.service;


import com.example.recipesapi.v1.recipe.exception.CustomNotFoundException;
import com.example.recipesapi.v1.recipe.model.dto.RecipeDto;
import com.example.recipesapi.v1.recipe.model.entity.Recipe;
import com.example.recipesapi.v1.recipe.repository.RecipeRepository;
import com.example.recipesapi.v1.recipe.service.RecipeService;
import com.example.recipesapi.v1.recipe.util.RecipeMapper;
import com.example.recipesapi.v1.security.model.CustomUserDetails;
import com.example.recipesapi.v1.security.model.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private RecipeMapper recipeMapper;
    @Mock
    private Authentication authentication;
    @Mock
    private CustomUserDetails customUserDetails;
    @Mock
    private User user;
    @InjectMocks
    private RecipeService underTestRecipeService;

    @Test
    void canAddRecipe() {
        //given
        Recipe recipe = new Recipe(1L,
                "Carroten soup",
                "Delicious tomate soup",
                "soup",
                List.of("Tomaten", "Peper", "sól"),
                List.of("Tomaten", "Peper", "sól"),
                LocalDateTime.now(), null);

        given(authentication.getPrincipal()).willReturn(customUserDetails);
        //when
        underTestRecipeService.addRecipe(recipe, authentication);
        //then
        ArgumentCaptor<Recipe> recipeArgumentCaptor = ArgumentCaptor.forClass(Recipe.class);

        verify(recipeRepository).save(recipeArgumentCaptor.capture());

        final Recipe capturedRecipe = recipeArgumentCaptor.getValue();
        assertThat(capturedRecipe).isEqualTo(recipe);
    }

    @Test
    void canGetAllRecipes() {
        //when
        underTestRecipeService.getAllRecipes();
        //then
        verify(recipeRepository).findAll();
    }

    @Test
    void canGetRecipesByNameContaining() {
        //given
        String name = "Recipe name";
        //when
        underTestRecipeService.getRecipesByNameContaining(name);
        //then
        ArgumentCaptor<String> listRecipesArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(recipeRepository).findByNameContainingIgnoreCaseOrderByDateDesc(listRecipesArgumentCaptor.capture());

        String capturedName = listRecipesArgumentCaptor.getValue();
        assertThat(capturedName).isEqualTo(name);
    }

    @Test
    void canGetRecipesByCategory() {
        //given
        String category = "category";
        //when
        underTestRecipeService.getRecipesByCategory(category);
        //then
        ArgumentCaptor<String> listRecipesArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(recipeRepository).findAllByCategoryIgnoreCaseOrderByDateDesc(listRecipesArgumentCaptor.capture());

        String capturedName = listRecipesArgumentCaptor.getValue();
        assertThat(capturedName).isEqualTo(category);
    }

    @Test
    void canUpdateWholeRecipe() {
        //given
        Long id = 1L;
        Recipe recipe = new Recipe(1L,
                "Carroten soup",
                "Delicious tomate soup",
                "soup",
                List.of("Tomaten", "Peper", "sól"),
                List.of("Tomaten", "Peper", "sól"),
                LocalDateTime.now(), null);

        Recipe newRecipe = new Recipe(1L,
                "Carroten soup edited",
                "Delicious tomate soup edited",
                "soup edited",
                List.of("Tomaten1", "Peper1", "sól1"),
                List.of("Tomaten1", "Peper1", "sól1"),
                LocalDateTime.now(), null);

        given(recipeRepository.findById(anyLong())).willReturn(Optional.of(recipe));
        //then
        underTestRecipeService.updateWholeRecipe(id, newRecipe);

        ArgumentCaptor<Recipe> recipeArgumentCaptor = ArgumentCaptor.forClass(Recipe.class);
        verify(recipeRepository).save(recipeArgumentCaptor.capture());
        Recipe capturedRecipe = recipeArgumentCaptor.getValue();
        assertThat(capturedRecipe).isEqualTo(newRecipe);
    }

    @Test
    void updateWholeRecipeWillThrowWhenInvalidId() {
        //given
        Long id = 1L;
        given(recipeRepository.findById(anyLong())).willReturn(Optional.empty());
        //when
        //then
        assertThatThrownBy(() -> underTestRecipeService.updateWholeRecipe(id, any()))
                .isInstanceOf(CustomNotFoundException.class)
                .hasMessageContaining("Not found recipe with id: " + id);
    }

    @Test
    void canGetRecipeById() {
        //given
        Long id = 1L;
        Recipe recipe = new Recipe(1L,
                "Carroten soup",
                "Delicious tomate soup",
                "soup",
                List.of("Tomaten", "Peper", "sól"),
                List.of("Tomaten", "Peper", "sól"),
                LocalDateTime.now(), null);

        RecipeDto recipeDto = new RecipeDto(
                "Carroten soup",
                "Delicious tomate soup",
                "soup",
                List.of("Tomaten", "Peper", "sól"),
                List.of("Tomaten", "Peper", "sól"),
                LocalDateTime.now());

        given(recipeRepository.findById(anyLong())).willReturn(Optional.of(recipe));
        given(recipeMapper.convertToDto(any())).willReturn(recipeDto);
        //when
        underTestRecipeService.getRecipeDtoById(id);
        //then
        ArgumentCaptor<Long> recipeIdArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(recipeRepository).findById(recipeIdArgumentCaptor.capture());
        Long capturedId = recipeIdArgumentCaptor.getValue();
        assertThat(capturedId).isEqualTo(id);
    }

    @Test
    void getRecipeByIdWillThrowWhenCantFind() {
        //give
        Long id = 1L;
        Recipe recipe = new Recipe(1L,
                "Carroten soup",
                "Delicious tomate soup",
                "soup",
                List.of("Tomaten", "Peper", "sól"),
                List.of("Tomaten", "Peper", "sól"),
                LocalDateTime.now(), null);

        given(recipeRepository.findById(id)).willReturn(Optional.empty());
        //when
        //then
        assertThatThrownBy(() -> underTestRecipeService.getRecipeDtoById(recipe.getId()))
                .isInstanceOf(CustomNotFoundException.class)
                .hasMessageContaining("Not found recipe with id: " + id);
    }

    @Test
    void canRemoveRecipe() {
        //given
        Recipe recipe = new Recipe(1L,
                "Carroten soup",
                "Delicious tomate soup",
                "soup",
                List.of("Tomaten", "Peper", "sól"),
                List.of("Tomaten", "Peper", "sól"),
                LocalDateTime.now(), null);

        given(recipeRepository.findById(recipe.getId())).willReturn(Optional.of(recipe));
        //when
        underTestRecipeService.deleteRecipe(recipe.getId());
        //then

        ArgumentCaptor<Long> recipeIdArgumentCaptor = ArgumentCaptor.forClass(Long.class);

        verify(recipeRepository).deleteById(recipeIdArgumentCaptor.capture());
        final Long capturedId = recipeIdArgumentCaptor.getValue();
        assertThat(capturedId).isEqualTo(recipe.getId());
    }

    @Test
    void removeRecipeWillThrowIfInvalidId() {
        //given
        Recipe recipe = new Recipe(1L,
                "Carroten soup",
                "Delicious tomate soup",
                "soup",
                List.of("Tomaten", "Peper", "sól"),
                List.of("Tomaten", "Peper", "sól"),
                LocalDateTime.now(), null);

        given(recipeRepository.findById(anyLong())).willReturn(Optional.empty());
        //when
        //then
        assertThatThrownBy(() -> underTestRecipeService.deleteRecipe(recipe.getId()))
                .isInstanceOf(CustomNotFoundException.class)
                .hasMessageContaining("Not found recipe with id: " + recipe.getId());

        verify(recipeRepository, never()).deleteById(any());

    }

    @Test
    void canGetAuthenticatedUserRecipes() {
        //given
        Long userId = 1L;

        given(authentication.getPrincipal()).willReturn(customUserDetails);
        given(customUserDetails.getUser()).willReturn(user);
        given(user.getId()).willReturn(userId);
        //when
        underTestRecipeService.getAuthenticatedUserRecipes(authentication);
        //then
        ArgumentCaptor<Long> userIdArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(recipeRepository).findAllByUserId(userIdArgumentCaptor.capture());
        final Long userIdArgumentCaptorValue = userIdArgumentCaptor.getValue();
        assertThat(userIdArgumentCaptorValue).isEqualTo(userId);
    }

}