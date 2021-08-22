package com.recipes.model;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
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
}
