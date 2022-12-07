package com.example.Recipe.services.Impl;

import com.example.Recipe.log.annotation.LogAnnotation;
import com.example.Recipe.mapper.RecipeMapper;
import com.example.Recipe.model.RecipeDTO;
import com.example.Recipe.repository.RecipeRepository;
import com.example.Recipe.services.RecipeService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@LogAnnotation
@Service
@Transactional
public class RecipeServiceImpl implements RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private RecipeMapper recipeMapper;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeMapper recipeMapper){
        this.recipeRepository = recipeRepository;
        this.recipeMapper = recipeMapper;
    }
    @Override
    public RecipeDTO addRecipe(RecipeDTO recipeDTO) {
            recipeDTO = setLocalDateNow(recipeDTO);
            return recipeMapper.mapToDTO(recipeRepository
                    .save(recipeMapper.mapToDB(recipeDTO)));
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
        if (isExitsById(id)) {
            recipeDTO.setId(id);
            recipeDTO = setLocalDateNow(recipeDTO);
            recipeMapper.mapToDTO(recipeRepository
                    .save(recipeMapper.mapToDB(recipeDTO)));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
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


}
