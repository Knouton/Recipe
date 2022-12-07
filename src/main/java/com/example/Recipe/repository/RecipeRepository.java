package com.example.Recipe.repository;

import com.example.Recipe.repository.Entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
        List<Recipe> findByCategoryIgnoreCaseOrderByDateDesc(String category);
        List<Recipe> findByNameContainingIgnoreCaseOrderByDateDesc(String name);
    }
