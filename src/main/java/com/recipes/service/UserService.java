package com.recipes.service;


import com.recipes.model.Recipe;
import com.recipes.model.User;
import com.recipes.reposiroty.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    final
    UserRepository userRepository;
    final
    PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public boolean isAuthorized(){
        return getCurrentUserName() != null;
    }

    public Optional<User> findUserbyName(String name) {
        return userRepository.findByUserName(name);
    }

    public String getCurrentUserName() {
        Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principle instanceof UserDetails) {
            return ((UserDetails) principle).getUsername();
        } else {
            return null;
        }
    }

    public User getInstanceOfCurrentUser() {
        Optional<User> user = findUserbyName(getCurrentUserName());
        return user.get();
    }

    public void deleteRecipe(Recipe recipe) {
        User user = getInstanceOfCurrentUser();
        List<Recipe> recipes = user.getRecipes();
        int index = recipes.indexOf(recipe);
        recipes.remove(index);
        user.setRecipes(recipes);
        userRepository.save(user);
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

}

