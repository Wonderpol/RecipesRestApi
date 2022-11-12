package com.example.recipesapi.controller;

import com.example.recipesapi.model.Recipe;
import com.example.recipesapi.model.dto.RecipeDto;
import com.example.recipesapi.service.RecipeService;
import com.example.recipesapi.util.RecipeMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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

    @PostMapping("/new")
    private ResponseEntity<Map<String, Long>> newRecipe(@RequestBody Recipe recipe) {
        return new ResponseEntity<>(recipeService.addRecipe(recipe), HttpStatus.OK);
    }

    @GetMapping("{id}")
    private ResponseEntity<RecipeDto> getRecipeById(@PathVariable Long id) {
        final RecipeDto recipeDto = recipeService.getRecipeById(id);
        return new ResponseEntity<>(recipeDto, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void removeRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
    }
}
