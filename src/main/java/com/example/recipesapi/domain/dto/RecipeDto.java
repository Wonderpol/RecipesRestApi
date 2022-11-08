package com.example.recipesapi.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ElementCollection;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RecipeDto {
    private String name;
    private String description;
    @ElementCollection
    private List<String> ingredients = new ArrayList<>();
    @ElementCollection
    private List<String> directions = new ArrayList<>();
}
