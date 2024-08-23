package com.swiggy.app.demo.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FoodItemDto {

    private Long id;                  // Unique identifier for the food item
    private String name;              // Name of the food item
    private Double price;             // Price of the food item
    private String type;              // Starter, Main Course, or Dessert
    private Long menuId;              // Foreign key to the Menu entity
}