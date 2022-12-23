package com.example.recipesapi.recipe.util;

import com.example.recipesapi.recipe.model.entity.Recipe;
import com.example.recipesapi.recipe.model.dto.RecipeDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RecipeMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public RecipeMapper(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public RecipeDto convertToDto(Recipe recipe) {
        return modelMapper.map(recipe, RecipeDto.class);
    }

}
