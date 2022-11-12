package com.example.recipesapi.repository;

import com.example.recipesapi.model.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class RecipeRepositoryTest {

    @Autowired
    private RecipeRepository recipeRepositoryUnderTest;

    @BeforeEach
    void setUp() {

    }

    @Test
    void shouldFindSingleRecipeByName() {
        //given
        String searchName = "Tomato soup";

        List<String> ingredients = Arrays.asList("1. ", "2. ");
        List<String> directions = Arrays.asList("1. ", "2. ");

        Recipe recipe1 = new Recipe(
                1L,
                "Tomato soup",
                "Delicious tomato soup",
                ingredients,
                directions
        );

        Recipe recipe2 = new Recipe(
                2L,
                "Mushrooms soup",
                "Delicious mushrooms soup",
                ingredients,
                directions
        );

        List<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe1);
        recipes.add(recipe2);

        recipeRepositoryUnderTest.saveAll(recipes);
        //when
        List<Recipe> recipesList = recipeRepositoryUnderTest.findRecipeByName(searchName.toLowerCase());
        //then
        assertThat(recipesList).isEqualTo(List.of(recipe1));
    }

    @Test
    void shouldFindTwoRecipesByName() {
        //given
        String searchName = "soup";

        List<String> ingredients = Arrays.asList("1. ", "2. ");
        List<String> directions = Arrays.asList("1. ", "2. ");

        Recipe recipe1 = new Recipe(
                1L,
                "Tomato soup",
                "Delicious tomato soup",
                ingredients,
                directions
        );

        Recipe recipe2 = new Recipe(
                2L,
                "Mushrooms soup",
                "Delicious mushrooms soup",
                ingredients,
                directions
        );

        List<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe1);
        recipes.add(recipe2);

        recipeRepositoryUnderTest.saveAll(recipes);
        //when
        List<Recipe> recipesList = recipeRepositoryUnderTest.findRecipeByName(searchName.toLowerCase());
        //then
        assertThat(recipesList).isEqualTo(recipes);
    }

    @Test
    void shouldFindRecipesThatHasSearchLettersInName() {
        //given
        String searchName = "oup";

        List<String> ingredients = Arrays.asList("1. ", "2. ");
        List<String> directions = Arrays.asList("1. ", "2. ");

        Recipe recipe1 = new Recipe(
                1L,
                "Tomato soup",
                "Delicious tomato soup",
                ingredients,
                directions
        );

        Recipe recipe2 = new Recipe(
                2L,
                "Mushrooms soup",
                "Delicious mushrooms soup",
                ingredients,
                directions
        );

        List<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe1);
        recipes.add(recipe2);

        recipeRepositoryUnderTest.saveAll(recipes);
        //when
        List<Recipe> recipesList = recipeRepositoryUnderTest.findRecipeByName(searchName.toLowerCase());
        //then
        assertThat(recipesList).isEqualTo(recipes);
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