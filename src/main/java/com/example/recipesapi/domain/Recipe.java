package com.example.recipesapi.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Recipe {
    private String name;
    private String description;
    private List<String> ingredients = new ArrayList<>();
    private List<String> directions = new ArrayList<>();
}
