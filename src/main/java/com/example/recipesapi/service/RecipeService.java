package com.example.recipesapi.service;

import com.example.recipesapi.exception.CustomNotFoundException;
import com.example.recipesapi.model.Recipe;
import com.example.recipesapi.model.dto.RecipeDto;
import com.example.recipesapi.repository.RecipeRepository;
import com.example.recipesapi.util.RecipeMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
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
                .orElseThrow(() -> {
                    log.error("Can't find recipe with id: " + id);
                    throw new CustomNotFoundException();
                });
    }

    public void addRecipe(Recipe recipe) {
        recipeRepository.save(recipe);
    }

    public void deleteRecipe(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                        .orElseThrow(() -> {
                            log.error("Can't remove recipe with id: " + id);
                            throw new CustomNotFoundException();
                        });

        recipeRepository.deleteById(recipe.getId());

    }

    public List<Recipe> getRecipeByName(String name) {
        return recipeRepository.findByNameContaining(name);
    }

}
