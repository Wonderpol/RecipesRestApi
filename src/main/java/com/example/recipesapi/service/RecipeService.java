package com.example.recipesapi.service;

import com.example.recipesapi.exception.CustomNotFoundException;
import com.example.recipesapi.model.Recipe;
import com.example.recipesapi.model.dto.RecipeDto;
import com.example.recipesapi.repository.RecipeRepository;
import com.example.recipesapi.util.RecipeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;

    @Autowired
    public RecipeService(final RecipeRepository recipeRepository, final RecipeMapper recipeMapper) {
        this.recipeRepository = recipeRepository;
        this.recipeMapper = recipeMapper;
    }

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public RecipeDto getRecipeById(Long id) {
        return recipeRepository.findById(id)
                .map(recipeMapper::convertToDto)
                .orElseThrow(CustomNotFoundException::new);
    }

    public Map<String, Long> addRecipe(Recipe recipe) {
        final Recipe savedRecipe = recipeRepository.save(recipe);
        return Collections.singletonMap("id", savedRecipe.getId());
    }

    public void deleteRecipe(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                        .orElseThrow(CustomNotFoundException::new);

        recipeRepository.deleteById(recipe.getId());

    }

}
