package com.example.recipesapi.service;

import com.example.recipesapi.domain.Recipe;
import com.example.recipesapi.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(final RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Map<String, Integer> addNewRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public Recipe getRecipeById(Integer id) {
        return recipeRepository.getRecipeById(id);
    }

}
