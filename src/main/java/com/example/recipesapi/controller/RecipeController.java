package com.example.recipesapi.controller;

import com.example.recipesapi.domain.Recipe;
import com.example.recipesapi.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(final RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/new")
    private Map<String, Integer> newRecipe(@RequestBody Recipe recipe) {
        return recipeService.addNewRecipe(recipe);
    }

    @GetMapping("{id}")
    private ResponseEntity<Recipe> recipe(@PathVariable Integer id) {
        final Recipe recipeById = recipeService.getRecipeById(id);
        if (recipeById != null) {
            return new ResponseEntity<>(recipeById, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
