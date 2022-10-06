package com.example.recipesapi.service;

import com.example.recipesapi.domain.Recipe;
import com.example.recipesapi.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(final RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipe addNewRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public Recipe getRecipe() {
        return recipeRepository.getRecipe();
    }

}
