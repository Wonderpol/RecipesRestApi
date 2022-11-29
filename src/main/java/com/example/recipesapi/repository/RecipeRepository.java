package com.example.recipesapi.repository;

import com.example.recipesapi.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByNameContainingOrderByDateDesc(String name);

    List<Recipe> findAllByCategoryOrderByDateDesc(String category);
}
