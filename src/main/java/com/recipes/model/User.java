package com.recipes.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class User {

    @Id
    @NotBlank
    private String userName;

    @Column
    @Size(min = 8)
    @NotBlank
    private String password;

    @Column
    private boolean isActive = true;

    @Column
    private String roles = "ROLE_USER";

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Recipe> recipes = new ArrayList<>();

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
