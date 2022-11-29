package com.example.recipesapi.service;


import com.example.recipesapi.exception.CustomNotFoundException;
import com.example.recipesapi.model.Recipe;
import com.example.recipesapi.model.dto.RecipeDto;
import com.example.recipesapi.repository.RecipeRepository;
import com.example.recipesapi.util.RecipeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @Mock private RecipeRepository recipeRepository;
    @Mock
    private RecipeMapper recipeMapper;
    private RecipeService underTestRecipeService;

    @BeforeEach
    void setUp() {
        underTestRecipeService = new RecipeService(recipeRepository, recipeMapper);
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
        verify(recipeRepository).findByNameContainingOrderByDateDesc(listRecipesArgumentCaptor.capture());

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
        verify(recipeRepository).findAllByCategoryOrderByDateDesc(listRecipesArgumentCaptor.capture());

        String capturedName = listRecipesArgumentCaptor.getValue();
        assertThat(capturedName).isEqualTo(category);
    }

    @Test
    void canGetRecipeById() {
        //give
        Long id = 1L;
        Recipe recipe = new Recipe(1L,
                "Carroten soup",
                "Delicious tomate soup",
                "soup",
                List.of("Tomaten", "Peper", "sól"),
                List.of("Tomaten", "Peper", "sól"),
                LocalDateTime.now());

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
        underTestRecipeService.getRecipeById(id);
        //then
        ArgumentCaptor<Long> recipeIdArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(recipeRepository).findById(recipeIdArgumentCaptor.capture());
        Long capturedId = recipeIdArgumentCaptor.getValue();
        assertThat(capturedId).isEqualTo(id);
    }

    @Test
    void canGetRecipeByIdWillThrowWhenCantFind() {
        //give
        Long id = 1L;
        Recipe recipe = new Recipe(1L,
                "Carroten soup",
                "Delicious tomate soup",
                "soup",
                List.of("Tomaten", "Peper", "sól"),
                List.of("Tomaten", "Peper", "sól"),
                LocalDateTime.now());

        given(recipeRepository.findById(id)).willReturn(Optional.empty());
        //when
        //then
        assertThatThrownBy(() -> underTestRecipeService.getRecipeById(recipe.getId()))
                .isInstanceOf(CustomNotFoundException.class)
                .hasMessageContaining("Not found recipe with id: " + id);
    }

    @Test
    void canAddRecipe() {
        //given
        Recipe recipe = new Recipe(1L,
                "Carroten soup",
                "Delicious tomate soup",
                "soup",
                List.of("Tomaten", "Peper", "sól"),
                List.of("Tomaten", "Peper", "sól"),
                LocalDateTime.now());
        //when
        underTestRecipeService.addRecipe(recipe);
        //then
        ArgumentCaptor<Recipe> recipeArgumentCaptor = ArgumentCaptor.forClass(Recipe.class);

        verify(recipeRepository).save(recipeArgumentCaptor.capture());

        final Recipe capturedRecipe = recipeArgumentCaptor.getValue();
        assertThat(capturedRecipe).isEqualTo(recipe);
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
                LocalDateTime.now());

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
                LocalDateTime.now());

        given(recipeRepository.findById(anyLong())).willReturn(Optional.empty());
        //when
        //then
        assertThatThrownBy(() -> underTestRecipeService.deleteRecipe(recipe.getId()))
                .isInstanceOf(CustomNotFoundException.class)
                .hasMessageContaining("Not found recipe with id: " + recipe.getId());

        verify(recipeRepository, never()).deleteById(any());

    }

}