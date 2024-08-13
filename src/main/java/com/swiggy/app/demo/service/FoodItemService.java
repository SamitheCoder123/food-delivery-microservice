package com.swiggy.app.demo.service;

import com.swiggy.app.demo.Dto.FoodItemDto;

import java.util.List;

/**
 * @author
 **/
public interface FoodItemService {

    FoodItemDto createFoodItem(FoodItemDto foodItemDto);

    FoodItemDto getFoodItemById(Long id);

    List<FoodItemDto> getAllFoodItems();

    FoodItemDto updateFoodItem(Long id, FoodItemDto foodItemDto);

    void deleteFoodItem(Long id);
}
