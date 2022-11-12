package com.example.recipesapi.repository;

import com.example.recipesapi.model.Recipe;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RecipeRepositoryTest {

    @Autowired
    private RecipeRepository recipeRepositoryUnderTest;

    @AfterEach
    void tearDown() {
        recipeRepositoryUnderTest.deleteAll();
    }

    @Test
    void shouldFindSingleRecipeByName() {
        //given
        String searchName = "Tomato soup";

        Recipe recipe1 = new Recipe(
                1L,
                "Tomato soup",
                "Delicious tomato soup",
                Arrays.asList("1. ", "2. "),
                Arrays.asList("1. ", "2. ")
        );

        Recipe recipe2 = new Recipe(
                2L,
                "Mushrooms soup",
                "Delicious mushrooms soup",
                Arrays.asList("1. ", "2. "),
                Arrays.asList("1. ", "2. ")
        );

        List<Recipe> recipes = List.of(recipe1, recipe2);

        recipeRepositoryUnderTest.saveAll(recipes);

        //when
        List<Recipe> recipesList = recipeRepositoryUnderTest.findRecipeByName(searchName.toLowerCase());

        //then
        assertThat(recipesList).hasSize(1).contains(recipe1);
    }

    @Test
    void shouldFindTwoRecipesByLettersOfName() {
        //given
        String searchName = "oup";

        Recipe recipe1 = new Recipe(
                1L,
                "Tomato soup",
                "Delicious tomato soup",
                Arrays.asList("1. ", "2. "),
                Arrays.asList("1. ", "2. ")
        );

        Recipe recipe2 = new Recipe(
                2L,
                "Mushrooms soup",
                "Delicious mushrooms soup",
                Arrays.asList("1. ", "2. "),
                Arrays.asList("1. ", "2. ")
        );

        List<Recipe> recipes = List.of(recipe1, recipe2);

        recipeRepositoryUnderTest.saveAll(recipes);

        //when
        List<Recipe> recipesList = recipeRepositoryUnderTest.findRecipeByName(searchName.toLowerCase());

        //then
        assertThat(recipesList).hasSize(2).contains(recipe1, recipe2);
    }

    @Test
    void findByNameShouldReturnEmptyListOfRecipes() {
        //given
        String searchName = "Tomato soup";

        //when
        List<Recipe> recipesList = recipeRepositoryUnderTest.findRecipeByName(searchName.toLowerCase());

        //then
        assertThat(recipesList).isEqualTo(List.of());
    }

}