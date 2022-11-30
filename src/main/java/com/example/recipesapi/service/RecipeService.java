package com.example.recipesapi.service;

import com.example.recipesapi.exception.CustomNotFoundException;
import com.example.recipesapi.model.Recipe;
import com.example.recipesapi.model.dto.RecipeDto;
import com.example.recipesapi.repository.RecipeRepository;
import com.example.recipesapi.util.RecipeMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public RecipeDto getRecipeDtoById(Long id) {
        return recipeRepository.findById(id)
                .map(recipeMapper::convertToDto)
                .orElseThrow(() -> {
                    log.error("Can't find recipe with id: " + id);
                    throw new CustomNotFoundException("Not found recipe with id: " + id);
                });
    }

    private Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Can't find recipe with id: " + id);
                    throw new CustomNotFoundException("Not found recipe with id: " + id);
                });
    }

    public void addRecipe(Recipe recipe) {
        recipeRepository.save(recipe);
    }

    //TODO: Replace fetching recipe with function getRecipeById that already throws to make it DRY
    public void deleteRecipe(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                        .orElseThrow(() -> {
                            log.error("Can't remove recipe with id: " + id);
                            throw new CustomNotFoundException("Not found recipe with id: " + id);
                        });

        recipeRepository.deleteById(recipe.getId());

    }

    public void updateWholeRecipe(Long id, Recipe modifiedRecipe) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Can't remove recipe with id: " + id);
                    throw new CustomNotFoundException("Not found recipe with id: " + id);
                });
        recipe.setName(modifiedRecipe.getName());
        recipe.setDescription(modifiedRecipe.getDescription());
        recipe.setCategory(modifiedRecipe.getCategory());
        recipe.setIngredients(modifiedRecipe.getIngredients());
        recipe.setDirections(modifiedRecipe.getDirections());

        recipeRepository.save(recipe);
    }

    public List<Recipe> getRecipesByNameContaining(String name) {
        return recipeRepository.findByNameContainingOrderByDateDesc(name);
    }

    public List<Recipe> getRecipesByCategory(String category) {
        return recipeRepository.findAllByCategoryOrderByDateDesc(category);
    }
}
