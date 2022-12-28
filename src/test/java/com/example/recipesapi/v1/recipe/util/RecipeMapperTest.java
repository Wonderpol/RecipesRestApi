package com.example.recipesapi.v1.recipe.util;

import com.example.recipesapi.v1.recipe.model.entity.Recipe;
import com.example.recipesapi.v1.recipe.util.RecipeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RecipeMapperTest {

    @Mock
    private ModelMapper modelMapper;
    private RecipeMapper recipeMapperUnderTest;

    @BeforeEach
    void setUp() {
        recipeMapperUnderTest = new RecipeMapper(modelMapper);
    }

    @Test
    void convertToDtoShouldReturnRecipeDto() {
        //given

        Recipe recipe = new Recipe(1L,
                "Carroten soup",
                "Delicious tomate soup",
                "soup",
                List.of("Tomaten", "Peper", "sól"),
                List.of("Tomaten", "Peper", "sól"),
                LocalDateTime.now(), null);
        //when
        recipeMapperUnderTest.convertToDto(recipe);

        //then
        ArgumentCaptor<Recipe> recipeArgumentCaptor = ArgumentCaptor.forClass(Recipe.class);
        verify(modelMapper).map(recipeArgumentCaptor.capture(), any());

        Recipe capturedRecipe = recipeArgumentCaptor.getValue();
        assertThat(capturedRecipe).isEqualTo(recipe);
    }

}