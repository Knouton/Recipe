package com.example.Recipe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.example.Recipe.mapper.RecipeMapper;
import com.example.Recipe.model.RecipeDTO;
import com.example.Recipe.repository.Entity.Recipe;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

public class RecipeMapperTest {
	@InjectMocks
	private RecipeMapper recipeMapper;

	private Recipe recipe;
	private RecipeDTO recipeDTO;

	@BeforeEach
	public void setup(){
		recipe = Recipe.builder()
				.id(1)
				.name("Mint Tea")
				.category("beverage")
				.description("Mint Tea 1")
				.ingredients("filtered water;honey;mint leaves;")
				.directions("Boil das;Pour boiling hot water into a mug;Add fresh mint leaves;Mix and let the mint leaves seep for 3-5 minutes;Add honey and mix again;")
				.build();
		recipeDTO = RecipeDTO.builder()
				.id(1)
				.name("Mint Tea")
				.category("beverage")
				.description("Mint Tea 1")
				.ingredients(List.of("filtered water", "honey", "mint leaves"))
				.directions(List.of("Boil das", "Pour boiling hot water into a mug", "Add fresh mint leaves", "Mix and let the mint leaves seep for 3-5 minutes", "Add honey and mix again"))
				.build();
		recipeMapper = new RecipeMapper();
	}

	@DisplayName("JUnit test for add method")
	@Test
	public void mapToDB() {
		Recipe recipeAfterMapping = recipeMapper.mapToDB(recipeDTO);
		assertThat(recipeAfterMapping).isEqualTo(recipe);
	}

	@DisplayName("JUnit test for add method")
	@Test
	public void mapToDTO() {
		RecipeDTO recipeDTOAfterMapping = recipeMapper.mapToDTO(recipe);
		assertThat(recipeDTOAfterMapping).isEqualTo(recipeDTO);
	}
}
