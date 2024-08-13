package com.swiggy.app.demo.controller;

import com.swiggy.app.demo.Dto.FoodItemDto;
import com.swiggy.app.demo.service.FoodItemService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return ResponseEntity.ok(foodItemService.createFoodItem(foodItemDto));
    }

    // Get a FoodItem by ID
    @GetMapping("/{id}")
    public ResponseEntity<FoodItemDto> getFoodItemById(@PathVariable Long id) {
        return ResponseEntity.ok(foodItemService.getFoodItemById(id));
    }

    // Get all FoodItems
    @GetMapping
    public ResponseEntity<List<FoodItemDto>> getAllFoodItems() {
        return ResponseEntity.ok(foodItemService.getAllFoodItems());
    }

    // Update a FoodItem
    @PutMapping("/{id}")
    public ResponseEntity<FoodItemDto> updateFoodItem(@PathVariable Long id, @RequestBody FoodItemDto foodItemDto) {
        return ResponseEntity.ok(foodItemService.updateFoodItem(id, foodItemDto));
    }

    // Delete a FoodItem
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFoodItem(@PathVariable Long id) {
        foodItemService.deleteFoodItem(id);
        return ResponseEntity.noContent().build();
    }
}