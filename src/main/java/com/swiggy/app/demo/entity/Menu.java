package com.swiggy.app.demo.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * @author
 **/
@Entity
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "menus")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "category", nullable = false)
    private String category; // Veg or Non-Veg

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
   // @JsonIgnore
    private Restaurant restaurant;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FoodItem> foodItems;

}