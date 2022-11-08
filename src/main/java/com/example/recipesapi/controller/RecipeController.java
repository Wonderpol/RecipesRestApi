package com.example.recipesapi.controller;

import com.example.recipesapi.domain.Recipe;
import com.example.recipesapi.domain.dto.RecipeDto;
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
    private final RecipeMapper recipeMapper;

    @Autowired
    public RecipeController(final RecipeService recipeService, final RecipeMapper recipeMapper, final ModelMapper modelMapper1) {
        this.recipeService = recipeService;
        this.recipeMapper = recipeMapper;
    }

    @GetMapping
    private ResponseEntity<List<Recipe>> allRecipes() {
        return new ResponseEntity<>(recipeService.getAllRecipes(), HttpStatus.OK);
    }

    @PostMapping("/new")
    private ResponseEntity<Map<String, Long>> newRecipe(@RequestBody Recipe recipe) {
        Recipe savedRecipe = recipeService.addRecipe(recipe);
        return new ResponseEntity<>(Collections.singletonMap("id", savedRecipe.getId()), HttpStatus.OK);
    }

    @GetMapping("{id}")
    private ResponseEntity<RecipeDto> getRecipeById(@PathVariable Long id) {
        return recipeService.getRecipeById(id)
                .map(recipeMapper::convertToDto)
                .map(recipe -> new ResponseEntity<>(recipe, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void removeRecipe(@PathVariable Long id) {
         recipeService.getRecipeById(id)
                .map(recipe -> {
                    recipeService.deleteRecipe(recipe.getId());
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
