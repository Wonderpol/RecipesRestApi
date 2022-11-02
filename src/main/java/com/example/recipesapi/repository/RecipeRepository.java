package com.example.recipesapi.repository;

import com.example.recipesapi.domain.Recipe;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Repository
public class RecipeRepository {

    private final Map<Integer, Recipe> recipes = new HashMap<>();

    public Map<String, Integer> save(Recipe recipe) {
        Integer id = recipes.size() + 1;
        recipes.put(id, recipe);
        return Collections.singletonMap("id", id);
    }

    public Recipe getRecipeById(Integer id) {
        return recipes.get(id);
    }
}
