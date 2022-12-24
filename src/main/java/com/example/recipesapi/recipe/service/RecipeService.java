package com.example.recipesapi.recipe.service;

import com.example.recipesapi.recipe.exception.CustomNotFoundException;
import com.example.recipesapi.recipe.model.entity.Recipe;
import com.example.recipesapi.recipe.model.dto.RecipeDto;
import com.example.recipesapi.recipe.repository.RecipeRepository;
import com.example.recipesapi.security.model.CustomUserDetails;
import com.example.recipesapi.security.repository.UserRepository;
import com.example.recipesapi.recipe.util.RecipeMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j2
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;

    @Autowired
    public RecipeService(final RecipeRepository recipeRepository, final RecipeMapper recipeMapper,
                         final UserRepository userRepository) {
        this.recipeRepository = recipeRepository;
        this.recipeMapper = recipeMapper;
    }

    public List<RecipeDto> getAllRecipes() {
        return recipeRepository.findAll()
                .stream()
                .map(recipeMapper::convertToDto)
                .toList();
    }

    public RecipeDto getRecipeDtoById(Long id) {
        return recipeMapper.convertToDto(getRecipeById(id));
    }

    public void addRecipe(Recipe recipe, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        recipe.setUser(userDetails.getUser());
        recipeRepository.save(recipe);
    }

    @PreAuthorize("isOwner(#id)")
    public void deleteRecipe(Long id) {
        Recipe recipe = getRecipeById(id);

        recipeRepository.deleteById(recipe.getId());

    }

    @PreAuthorize("isOwner(#id)")
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

    public Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Can't find recipe with id: " + id);
                    throw new CustomNotFoundException("Not found recipe with id: " + id);
                });
    }

    public List<Recipe> getAuthenticatedUserRecipes(Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = customUserDetails.getUser().getId();
        return recipeRepository.findAllByUserId(userId);
    }

}
