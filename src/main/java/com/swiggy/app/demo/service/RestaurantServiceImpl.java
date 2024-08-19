package com.swiggy.app.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiggy.app.demo.Dto.RestaurantDto;
import com.swiggy.app.demo.entity.FoodItem;
import com.swiggy.app.demo.entity.Menu;
import com.swiggy.app.demo.entity.Restaurant;
import com.swiggy.app.demo.repository.RestaurantRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author
 **/
@Service
@Transactional
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepo restaurantRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public RestaurantDto createRestaurant(RestaurantDto restaurantDto) {
        Restaurant restaurant = objectMapper.convertValue(restaurantDto, Restaurant.class);

        // Set the bi-directional relationship
        if (restaurant.getMenus() != null) {
            for (Menu menu : restaurant.getMenus()) {
                menu.setRestaurant(restaurant);
                if (menu.getFoodItems() != null) {
                    for (FoodItem foodItem : menu.getFoodItems()) {
                        foodItem.setMenu(menu);
                    }
                }
            }
        }

        restaurant = restaurantRepository.save(restaurant);
        return objectMapper.convertValue(restaurant, RestaurantDto.class);
    }

    @Override
    public RestaurantDto getRestaurantById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
        return objectMapper.convertValue(restaurant, RestaurantDto.class);
    }

    @Override
    public List<RestaurantDto> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurants.stream()
                .map(restaurant -> objectMapper.convertValue(restaurant, RestaurantDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public RestaurantDto updateRestaurant(Long id, RestaurantDto restaurantDto) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
        restaurant.setName(restaurantDto.getName());
        restaurant.setLocation(restaurantDto.getLocation());
        restaurant = restaurantRepository.save(restaurant);
        return objectMapper.convertValue(restaurant, RestaurantDto.class);
    }

    @Override
    public void deleteRestaurant(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
        restaurantRepository.delete(restaurant);
    }
}