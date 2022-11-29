package com.example.recipesapi.controller;

import com.example.recipesapi.model.Recipe;
import com.example.recipesapi.model.dto.RecipeDto;
import com.example.recipesapi.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
    private ResponseEntity<List<Recipe>> allRecipes() {
        return new ResponseEntity<>(recipeService.getAllRecipes(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    private ResponseEntity<RecipeDto> getRecipeById(@PathVariable Long id) {
        final RecipeDto recipeDto = recipeService.getRecipeById(id);
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
    private void newRecipe(@RequestBody Recipe recipe) {
        recipeService.addRecipe(recipe);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void removeRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
    }
}
