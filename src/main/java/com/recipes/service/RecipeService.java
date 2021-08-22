package com.recipes.service;

import com.recipes.model.Recipe;
import com.recipes.reposiroty.RecipeRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class RecipeService {
    final
    RecipeRepository recipeRepository;
    final
    UserService userService;

    public RecipeService(RecipeRepository recipeRepository, UserService userService) {
        this.recipeRepository = recipeRepository;
        this.userService = userService;
    }

    public int addRecipe(Recipe recipe) {
        if(recipe.getDateTime() == null) {
            LocalDateTime dateTime = LocalDateTime.now();
            recipe.setDateTime(dateTime.toString());
        }
        recipe.setUser(userService.getInstanceOfCurrentUser());
        Recipe newRecipe = recipeRepository.save(recipe);
        return newRecipe.getId();
    }

    public void updateRecipe(Recipe recipe) {
        LocalDateTime dateTime = LocalDateTime.now();
        recipe.setDateTime(dateTime.toString());
        recipe.setUser(userService.getInstanceOfCurrentUser());
        recipeRepository.save(recipe);
    }

    public Optional<Recipe> findRecipe(int id) {
        return recipeRepository.findById(id);
    }

    public List<Recipe> findByCategory(String category) {
        return recipeRepository.findAllByCategoryIgnoreCaseOrderByDateTimeDesc(category);
    }

    public List<Recipe> findByName(String name) {
        return recipeRepository.findByNameContainingIgnoreCaseOrderByDateTimeDesc(name);
    }

    public void deleteById(int id) {
        recipeRepository.deleteById(id);
    }

    public String getUserNameOfRecipeCreator(Recipe recipe) {
        return recipe.getUser().getUserName();
    }

    public Boolean isCurrentUserAuthorOfRecipe(Recipe recipe) {
        String currentUserName = userService.getCurrentUserName();
        String recipeCreator = recipe.getUser().getUserName();
        if(currentUserName.equals(recipeCreator)) {
            return true;
        } else {
            return false;
        }
    }

    public void deleteAll() {
        recipeRepository.deleteAll();
    }

}

