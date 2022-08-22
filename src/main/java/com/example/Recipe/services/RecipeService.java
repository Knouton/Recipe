package com.example.Recipe.services;

import com.example.Recipe.model.RecipeDTO;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface RecipeService {
    RecipeDTO addRecipe(RecipeDTO recipeDTO);
    void deleteRecipeById(int id);
    RecipeDTO getById(int id);
    List<RecipeDTO> getAll();

    boolean isExitsById(int id);
    @Modifying
    void updateRecipe(RecipeDTO recipeDTO, int id);

    List<RecipeDTO> getByCategory(String category);

    List<RecipeDTO> getByName(String name);
}
