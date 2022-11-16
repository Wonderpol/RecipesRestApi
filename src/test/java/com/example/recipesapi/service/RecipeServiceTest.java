package com.example.recipesapi.service;


import com.example.recipesapi.model.Recipe;
import com.example.recipesapi.repository.RecipeRepository;
import com.example.recipesapi.util.RecipeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
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
    void canAddRecipe() {
        //given
        Recipe recipe = new Recipe(1L,
                "Carroten soup",
                "Delicious tomate soup",
                List.of("Tomaten", "Peper", "sól"),
                List.of("1. Put 1kg tomate to thermomix", "2. Add salt"));
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
                List.of("Tomaten", "Peper", "sól"),
                List.of("1. Put 1kg tomate to thermomix", "2. Add salt"));

        given(recipeRepository.findById(recipe.getId())).willReturn(Optional.of(recipe));
        //when
        underTestRecipeService.deleteRecipe(recipe.getId());
        //then

        ArgumentCaptor<Long> recipeIdArgumentCaptor = ArgumentCaptor.forClass(Long.class);

        verify(recipeRepository).deleteById(recipeIdArgumentCaptor.capture());
        final Long capturedId = recipeIdArgumentCaptor.getValue();
        assertThat(capturedId).isEqualTo(recipe.getId());
    }

}