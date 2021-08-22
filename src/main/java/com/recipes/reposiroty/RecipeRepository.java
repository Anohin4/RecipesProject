package com.recipes.reposiroty;

import com.recipes.model.Recipe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RecipeRepository extends CrudRepository<Recipe, Integer> {
    public List<Recipe> findAllByCategoryIgnoreCaseOrderByDateTimeDesc(String category);
    public List<Recipe> findByNameContainingIgnoreCaseOrderByDateTimeDesc(String name);

}
