package com.swiggy.app.demo.service;

import com.swiggy.app.demo.Dto.RestaurantDto;

import java.util.List;

/**
 * @author
  **/

public interface RestaurantService {
    RestaurantDto createRestaurant(RestaurantDto restaurantDto);
    RestaurantDto getRestaurantById(Long id);
    List<RestaurantDto> getAllRestaurants();
    RestaurantDto updateRestaurant(Long id, RestaurantDto restaurantDto);
    void deleteRestaurant(Long id);
}
