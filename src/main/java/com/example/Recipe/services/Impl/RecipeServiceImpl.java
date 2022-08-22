package com.example.Recipe.services.Impl;

import com.example.Recipe.mapper.RecipeMapper;
import com.example.Recipe.model.RecipeDTO;
import com.example.Recipe.repository.RecipeRepository;
import com.example.Recipe.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private RecipeMapper recipeMapper;
    @Override
    public RecipeDTO addRecipe(RecipeDTO recipeDTO) {
        if (isValidRecipe(recipeDTO)){
            /*System.out.println(recipeDTO.getId() + " "+ recipeDTO.getName() + " " + recipeDTO.getDate());
            for (String ing:
                 ) {

            }*/
            recipeDTO = setLocalDateNow(recipeDTO);
            return recipeMapper.mapToDTO(recipeRepository
                    .save(recipeMapper.mapToDB(recipeDTO)));
        } else {
            throw new IllegalArgumentException();
        }
    }
    @Override
    public void deleteRecipeById(int id) {
        if (!isExitsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            recipeRepository.deleteById(id);
        }
    }
    @Override
    public RecipeDTO getById(int id) {
        return recipeMapper.mapToDTO(recipeRepository.getById(id));
    }

    @Override
    public List<RecipeDTO> getAll() {
        return null;
    }
    @Override
    public boolean isExitsById(int id){
        return recipeRepository.existsById(id);
    }

    @Override
    @Modifying
    public void updateRecipe(RecipeDTO recipeDTO, int id) {
        if (isValidRecipe(recipeDTO)) {
            if (isExitsById(id)){
                recipeDTO.setId(id);
                recipeDTO = setLocalDateNow(recipeDTO);
                recipeMapper.mapToDTO(recipeRepository
                        .save(recipeMapper.mapToDB(recipeDTO)));
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    //@Query(value = "select r from recipes r where r.category = ?1", nativeQuery = true)
    public List<RecipeDTO> getByCategory(String category) {
        return recipeRepository.findByCategoryIgnoreCaseOrderByDateDesc(category)
                .stream()
                .map(recipe -> recipeMapper.mapToDTO(recipe))
                .collect(Collectors.toList());
    }

    @Override
    public List<RecipeDTO> getByName(String name) {
        return recipeRepository.findByNameContainingIgnoreCaseOrderByDateDesc(name)
                .stream()
                .map(recipe -> recipeMapper.mapToDTO(recipe))
                .collect(Collectors.toList());
    }

    private RecipeDTO setLocalDateNow(RecipeDTO recipeDTO){
        recipeDTO.setDate(LocalDateTime.now());
        return recipeDTO;
    }

    public boolean isValidRecipe (RecipeDTO recipeDTO) {
        if (recipeDTO.getName() == null|| recipeDTO.getName().isBlank()
                || recipeDTO.getDescription() == null || recipeDTO.getDescription().isBlank()
                || recipeDTO.getCategory() == null || recipeDTO.getCategory().isBlank()
                || recipeDTO.getIngredients() == null || recipeDTO.getDirections() == null
                || recipeDTO.getIngredients().isEmpty() || recipeDTO.getDirections().isEmpty()
                || recipeDTO.getDate() != null) {
            throw new IllegalArgumentException();
        } else {
            return true;
        }
    }
}
