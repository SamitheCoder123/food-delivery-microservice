package com.swiggy.app.demo.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodItemDto {

    private Long id;                  // Unique identifier for the food item
    private String name;              // Name of the food item
    private Double price;             // Price of the food item
    private String description;       // Description of the food item (optional)
    private Long menuId;              // Foreign key to the Menu entity (associating the food item with a menu)
    private LocalDateTime createdAt;  // Timestamp when the food item was created
    private LocalDateTime updatedAt;  // Timestamp when the food item was last updated
}