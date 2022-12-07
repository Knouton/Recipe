package com.example.Recipe.controller;

import com.example.Recipe.events.publisher.CustomSpringEventPublisher;
import com.example.Recipe.model.RecipeDTO;
import com.example.Recipe.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/recipe/")
public class RecipeController {
    private final RecipeService recipeService;
    private  final CustomSpringEventPublisher customSpringEventPublisher;
    @Autowired
    public RecipeController(RecipeService recipeService, final CustomSpringEventPublisher customSpringEventPublisher) {
        this.recipeService = recipeService;
        this.customSpringEventPublisher = customSpringEventPublisher;
    }

    @PostMapping(value = "/new")
    public ResponseEntity<Map<String, Integer>> postRecipe(@RequestBody RecipeDTO recipeDTO) {
        try {
            recipeDTO = recipeService.addRecipe(recipeDTO);
            return new ResponseEntity<>(Map.of("id", recipeDTO.getId()), HttpStatus.OK);
        } catch (IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{id}")
    public RecipeDTO getRecipe(@PathVariable int id) {
        customSpringEventPublisher.publishCustomEvent("GetMapping :" + id);
        if (recipeService.isExitsById(id))
        {
            return recipeService.getById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteRecipe(@PathVariable int id) {
        recipeService.deleteRecipeById(id);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateRecipe(@PathVariable int id, @RequestBody RecipeDTO recipeDTO){
        try {
            recipeService.updateRecipe(recipeDTO, id);
        } catch (IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/search/")
    public List<RecipeDTO> searchByCategory(@Param("category") Optional<String> category,
                                            @Param("name") Optional<String> name){
        if (category.isPresent() && name.isEmpty()){
            return recipeService.getByCategory(category.get());
        } else if(category.isEmpty() && name.isPresent()){
            return recipeService.getByName(name.get());
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
}
