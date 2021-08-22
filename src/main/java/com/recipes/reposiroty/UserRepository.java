package com.recipes.reposiroty;

import com.recipes.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface UserRepository extends CrudRepository<User, Integer> {
    public Optional<User> findByUserName(String userName);
}
