package com.example.Recipe.mapper;


import com.example.Recipe.model.RecipeDTO;
import com.example.Recipe.repository.Entity.Recipe;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@AllArgsConstructor
public class RecipeMapper {

    public Recipe mapToDB(RecipeDTO recipeDTO){

        StringBuilder ingredientsData = new StringBuilder();
        recipeDTO.getIngredients().forEach(i -> ingredientsData.append(i).append(";"));

        StringBuilder directionsData = new StringBuilder();
        recipeDTO.getDirections().forEach(i -> directionsData.append(i).append(";"));

        return new Recipe(recipeDTO.getId(),
                recipeDTO.getName(),
                recipeDTO.getCategory(),
                recipeDTO.getDate(),
                recipeDTO.getDescription(),
                ingredientsData.toString(),
                directionsData.toString());
    }

    public RecipeDTO mapToDTO (Recipe recipeDB){
        return new RecipeDTO(recipeDB.getId(),
                recipeDB.getName(),
                recipeDB.getCategory(),
                recipeDB.getDate(),
                recipeDB.getDescription(),
                Arrays.asList(recipeDB.getIngredients().split(";")),
                Arrays.asList(recipeDB.getDirections().split(";")));
    }
}

