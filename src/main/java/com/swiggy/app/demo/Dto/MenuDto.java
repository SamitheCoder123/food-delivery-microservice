package com.swiggy.app.demo.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuDto {
    private Long id;                      // Unique identifier for the menu
    private String name;                  // Name of the menu
    private Long restaurantId;            // Foreign key to the Restaurant entity
    private List<FoodItemDto> foodItems;  // List of food items associated with this menu
}