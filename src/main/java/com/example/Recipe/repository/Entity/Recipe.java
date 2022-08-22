package com.example.Recipe.repository.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id", nullable = false)
    private int id;
    @Column
    private String name;
    @Column
    private String category;
    @Column
    private LocalDateTime date;
    @Column
    private String description;
    @Column
    private String ingredients;
    /*@ElementCollection
    private List<String> ingredients;*/
    @Column
    private String directions;
}
