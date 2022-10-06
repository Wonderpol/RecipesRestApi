package com.example.recipesapi.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Recipe {
    private String name;
    private String description;
    private String ingredients;
    private String directions;
}
