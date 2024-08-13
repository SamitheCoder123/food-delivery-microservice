package com.swiggy.app.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiggy.app.demo.Dto.FoodItemDto;
import com.swiggy.app.demo.Exception.ResourceNotFoundException;
import com.swiggy.app.demo.entity.FoodItem;
import com.swiggy.app.demo.repository.FoodItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author
 **/
@Service
public class FoodItemServiceImpl implements FoodItemService {

    @Autowired
    private FoodItemRepo foodItemRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public FoodItemDto createFoodItem(FoodItemDto foodItemDto) {
        FoodItem foodItem = objectMapper.convertValue(foodItemDto, FoodItem.class);
        foodItem = foodItemRepository.save(foodItem);
        return objectMapper.convertValue(foodItem, FoodItemDto.class);
    }

    @Override
    public FoodItemDto getFoodItemById(Long id) {
        FoodItem foodItem = foodItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food item not found"));
        return objectMapper.convertValue(foodItem, FoodItemDto.class);
    }

    @Override
    public List<FoodItemDto> getAllFoodItems() {
        List<FoodItem> foodItems = foodItemRepository.findAll();
        return foodItems.stream()
                .map(foodItem -> objectMapper.convertValue(foodItem, FoodItemDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public FoodItemDto updateFoodItem(Long id, FoodItemDto foodItemDto) {
        FoodItem foodItem = foodItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food item not found"));
        foodItem.setName(foodItemDto.getName());
        foodItem.setPrice(foodItemDto.getPrice());
        foodItem = foodItemRepository.save(foodItem);
        return objectMapper.convertValue(foodItem, FoodItemDto.class);
    }

    @Override
    public void deleteFoodItem(Long id) {
        FoodItem foodItem = foodItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food item not found"));
        foodItemRepository.delete(foodItem);
    }
}
