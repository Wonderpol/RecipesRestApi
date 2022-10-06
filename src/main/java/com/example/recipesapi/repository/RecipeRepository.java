package com.example.recipesapi.repository;

import com.example.recipesapi.domain.Recipe;
import org.springframework.stereotype.Repository;

@Repository
public class RecipeRepository {
    private Recipe recipe;

    public Recipe save(Recipe recipe) {
        this.recipe = recipe;
        return this.recipe;
    }

    public Recipe getRecipe() {
        return this.recipe;
    }

}
