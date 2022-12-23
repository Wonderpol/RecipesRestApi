package com.example.recipesapi.recipe.controller;

import com.example.recipesapi.recipe.model.Recipe;
import com.example.recipesapi.recipe.model.dto.RecipeDto;
import com.example.recipesapi.recipe.model.dto.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(final RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    private ResponseEntity<List<RecipeDto>> allRecipes() {
        List<RecipeDto> recipes = recipeService.getAllRecipes();
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    @GetMapping("{id}")
    private ResponseEntity<RecipeDto> getRecipeById(@PathVariable Long id) {
        final RecipeDto recipeDto = recipeService.getRecipeDtoById(id);
        return new ResponseEntity<>(recipeDto, HttpStatus.OK);
    }

    @GetMapping(value = "/search/", params = "name")
    private ResponseEntity<List<Recipe>> getRecipeByName(@RequestParam String name) {
        final List<Recipe> recipe = recipeService.getRecipesByNameContaining(name);
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    @GetMapping(value = "/search/", params = "category")
    private ResponseEntity<List<Recipe>> getRecipesByCategory(@RequestParam String category) {
        final List<Recipe> recipe = recipeService.getRecipesByCategory(category);
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    @PostMapping("/new")
    private void newRecipe(@RequestBody Recipe recipe, Authentication authentication) {
        recipeService.addRecipe(recipe, authentication);
    }

    @PutMapping("{id}")
    private ResponseEntity<RecipeDto> updateRecipe(@PathVariable Long id, @RequestBody @Valid Recipe recipe) {
        recipeService.updateWholeRecipe(id, recipe);

        return new ResponseEntity<>(recipeService.getRecipeDtoById(id), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void removeRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
    }
}
