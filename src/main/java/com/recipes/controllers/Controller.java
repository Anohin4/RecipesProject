package com.recipes.controllers;

import com.recipes.model.Recipe;
import com.recipes.model.User;
import com.recipes.service.RecipeService;
import com.recipes.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

@RestController
public class Controller {
    final RecipeService recipeService;
    final UserService userService;

    public Controller(RecipeService recipeService, UserService userService) {
        this.recipeService = recipeService;
        this.userService = userService;
    }

    @GetMapping("/login")
    @ResponseBody
    public ModelAndView loginToSystem() {
        ModelAndView model = new ModelAndView();
        model.setViewName("login");
        return model;
    }

    @GetMapping("/api/testSecurity")
    public ResponseEntity getUserName() {
        return ResponseEntity
                .status(200)
                .body(userService.getCurrentUserName());
    }

    @GetMapping("/api/recipe/{id}")
    public ResponseEntity getRecipeById(@PathVariable int id) {
        Optional<Recipe> recipe = recipeService.findRecipe(id);
        if(recipe.isPresent()) {
            return ResponseEntity
                    .status(200)
                    .body(recipe.get());
        } else {
            return ResponseEntity
                    .status(404)
                    .body(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/api/recipe/myRecipe")
    public ResponseEntity getAllRecipeByCurrentUser() {
        User currentUser = userService.getInstanceOfCurrentUser();
        return ResponseEntity
                .status(200)
                .body(currentUser.getRecipes());
    }

    @GetMapping(value = "/api/recipe/search/", params = "category")
    public ResponseEntity searchRecipeByCategory(@RequestParam String category) {
        return ResponseEntity
                .status(200)
                .body(recipeService.findByCategory(category));
    }

    @GetMapping(value = "/api/recipe/search/", params = "name")
    public ResponseEntity searchRecipeByName(@RequestParam String name) {
        return ResponseEntity
                .status(200)
                .body(recipeService.findByName(name));
    }

    @PutMapping("api/recipe/{id}")
    public ResponseEntity updateRecipe(@PathVariable int id, @Valid @RequestBody Recipe newRecipe) {
        Optional<Recipe> recipe = recipeService.findRecipe(id);
        if(recipe.isEmpty()) {
            return ResponseEntity
                    .status(404)
                    .body(HttpStatus.NOT_FOUND);
        } else if (!recipeService.isCurrentUserAuthorOfRecipe(recipe.get())) {
            return ResponseEntity
                    .status(403)
                    .body(HttpStatus.FORBIDDEN);
        } else {
            newRecipe.setId(id);
            recipeService.updateRecipe(newRecipe);
            return ResponseEntity
                    .status(204)
                    .body(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/api/recipe/new")
    public ResponseEntity postRecipe(@Valid @RequestBody Recipe recipe) {
        return ResponseEntity
                .status(200)
                .body(Map.of("id", recipeService.addRecipe(recipe)));
    }

    @PostMapping("/api/register")
    public ResponseEntity createUser(@Valid @RequestBody User user) {
        Optional<User> newUser = userService.findUserbyName(user.getUserName());
        if (newUser.isPresent()) {
            return ResponseEntity
                    .status(400)
                    .body(HttpStatus.BAD_REQUEST);
        }
        userService.addUser(user);
        return ResponseEntity
                .status(200)
                .body(HttpStatus.OK);
    }

    @DeleteMapping("/api/deleteAllRecipes")
    public ResponseEntity deleteAllRecipes() {
        recipeService.deleteAll();
        return ResponseEntity
                .status(200)
                .body(HttpStatus.OK);
    }

    @DeleteMapping("/api/deleteAllUsers")
    public ResponseEntity deleteAllUsers() {
        userService.deleteAll();
        return ResponseEntity
                .status(200)
                .body(HttpStatus.OK);
    }

    @DeleteMapping("/api/recipe/{id}")
    public ResponseEntity deleteRecipeById (@Valid @PathVariable int id) {
        Optional<Recipe> recipe = recipeService.findRecipe(id);
        if(recipe.isEmpty()) {
            return ResponseEntity
                    .status(404)
                    .body(HttpStatus.NOT_FOUND);
        } else if (!recipeService.isCurrentUserAuthorOfRecipe(recipe.get())) {
            return ResponseEntity
                    .status(403)
                    .body(HttpStatus.FORBIDDEN);
        } else {
            userService.deleteRecipe(recipe.get());
            recipeService.deleteById(id);
            return ResponseEntity
                    .status(204)
                    .body(HttpStatus.NO_CONTENT);
        }
    }
}

