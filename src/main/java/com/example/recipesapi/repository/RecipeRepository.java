package com.example.recipesapi.repository;

import com.example.recipesapi.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query("SELECT r FROM Recipe r WHERE lower(r.name) LIKE %:name%")
    Optional<List<Recipe>> findRecipeByName(String name);

}
