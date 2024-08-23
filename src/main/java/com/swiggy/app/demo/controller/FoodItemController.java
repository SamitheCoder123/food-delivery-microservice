package com.swiggy.app.demo.controller;

import com.swiggy.app.demo.Dto.FoodItemDto;
import com.swiggy.app.demo.service.FoodItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author
 **/
@RestController
@RequestMapping("/api/food-items")
public class FoodItemController {

    @Autowired
    private FoodItemService foodItemService;

    // Create a new FoodItem
    @PostMapping
    public ResponseEntity<FoodItemDto> createFoodItem(@RequestBody FoodItemDto foodItemDto) {
        FoodItemDto createdFoodItem = foodItemService.createFoodItem(foodItemDto);
        return new ResponseEntity<>(createdFoodItem, HttpStatus.CREATED);
    }

    // Get a FoodItem by ID
    @GetMapping("/{id}")
    public ResponseEntity<FoodItemDto> getFoodItemById(@PathVariable Long id) {
        FoodItemDto foodItemDto = foodItemService.getFoodItemById(id);
        return new ResponseEntity<>(foodItemDto, HttpStatus.OK);
    }

    // Get all FoodItems
    @GetMapping
    public ResponseEntity<List<FoodItemDto>> getAllFoodItems() {
        List<FoodItemDto> foodItems = foodItemService.getAllFoodItems();
        return new ResponseEntity<>(foodItems, HttpStatus.OK);
    }

    // Update a FoodItem
    @PutMapping("/{id}")
    public ResponseEntity<FoodItemDto> updateFoodItem(@PathVariable Long id, @RequestBody FoodItemDto foodItemDto) {
        FoodItemDto updatedFoodItem = foodItemService.updateFoodItem(id, foodItemDto);
        return new ResponseEntity<>(updatedFoodItem, HttpStatus.OK);
    }

    // Delete a FoodItem
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFoodItem(@PathVariable Long id) {
        foodItemService.deleteFoodItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}