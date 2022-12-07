package com.example.recipesapi.recipe.repository;

import com.example.recipesapi.recipe.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByNameContainingIgnoreCaseOrderByDateDesc(String name);

    List<Recipe> findAllByCategoryIgnoreCaseOrderByDateDesc(String category);
}
