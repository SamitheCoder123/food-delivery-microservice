package com.swiggy.app.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author
 **/
@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "food_items")
public class FoodItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "type", nullable = false)
    private String type; // Starter, Main Course, or Dessert

    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    //@JsonIgnore
    private Menu menu;
}