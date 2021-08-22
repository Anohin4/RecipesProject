package com.recipes.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Entity
@Table(name = "recipe")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    int id;

    @Column
    @NotBlank(message = "Category is mandatory")
    String category;

    @Column
    String dateTime;

    @Column
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Column
    @NotBlank(message = "Description is mandatory")
    private String description;

    @Column
    @ElementCollection
    @NotEmpty
    private List<String> ingredients;

    @Column
    @ElementCollection
    @NotEmpty
    private List<String> directions;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @JsonGetter("date")
    public String getDateTime() {
        return dateTime;
    }
}
