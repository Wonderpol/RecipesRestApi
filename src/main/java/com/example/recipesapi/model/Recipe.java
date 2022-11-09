package com.example.recipesapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "Recipes")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    @NotBlank
    private String description;

    @NonNull
    @NotNull
    @Size(min = 1)
    @ElementCollection
    private List<String> ingredients = new ArrayList<>();

    @NonNull
    @NotNull
    @Size(min = 1)
    @ElementCollection
    private List<String> directions = new ArrayList<>();
}
