package com.example.recipesapi.recipe.service;

import com.example.recipesapi.recipe.exception.CustomNotFoundException;
import com.example.recipesapi.recipe.model.Recipe;
import com.example.recipesapi.recipe.model.dto.RecipeDto;
import com.example.recipesapi.recipe.repository.RecipeRepository;
import com.example.recipesapi.util.RecipeMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;int myArray [] = {1, 3, 5};


    @Autowired
    public RecipeService(final RecipeRepository recipeRepository, final RecipeMapper recipeMapper) {
        this.recipeRepository = recipeRepository;
        this.recipeMapper = recipeMapper;
    }

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public RecipeDto getRecipeDtoById(Long id) {
        return recipeMapper.convertToDto(getRecipeById(id));
    }

    public void addRecipe(Recipe recipe) {
        recipeRepository.save(recipe);
    }

    public void deleteRecipe(Long id) {
        Recipe recipe = getRecipeById(id);

        recipeRepository.deleteById(recipe.getId());

    }

    public void updateWholeRecipe(Long id, Recipe modifiedRecipe) {
        Recipe recipe = getRecipeById(id);

        recipe.setName(modifiedRecipe.getName());
        recipe.setDescription(modifiedRecipe.getDescription());
        recipe.setCategory(modifiedRecipe.getCategory());
        recipe.setIngredients(modifiedRecipe.getIngredients());
        recipe.setDirections(modifiedRecipe.getDirections());

        recipeRepository.save(recipe);
    }

    public List<Recipe> getRecipesByNameContaining(String name) {
        return recipeRepository.findByNameContainingIgnoreCaseOrderByDateDesc(name);
    }

    public List<Recipe> getRecipesByCategory(String category) {
        return recipeRepository.findAllByCategoryIgnoreCaseOrderByDateDesc(category);
    }

    private Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Can't find recipe with id: " + id);
                    throw new CustomNotFoundException("Not found recipe with id: " + id);
                });
    }
}
