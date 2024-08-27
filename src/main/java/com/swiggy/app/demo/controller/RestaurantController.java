package com.swiggy.app.demo.controller;

import com.swiggy.app.demo.Dto.RestaurantDto;
import com.swiggy.app.demo.entity.Restaurant;
import com.swiggy.app.demo.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author
 **/
@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<RestaurantDto> createRestaurant(@RequestBody RestaurantDto restaurantDto) {
        RestaurantDto createdRestaurant = restaurantService.createRestaurant(restaurantDto);
        return new ResponseEntity<>(createdRestaurant, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDto> getRestaurantById(@PathVariable Long id) {
        RestaurantDto restaurantDto = restaurantService.getRestaurantById(id);
        return new ResponseEntity<>(restaurantDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<RestaurantDto>> getAllRestaurants() {
        List<RestaurantDto> restaurantDtos = restaurantService.getAllRestaurants();
        return new ResponseEntity<>(restaurantDtos, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantDto> updateRestaurant(@PathVariable Long id, @RequestBody RestaurantDto restaurantDto) {
        RestaurantDto updatedRestaurant = restaurantService.updateRestaurant(id, restaurantDto);
        return new ResponseEntity<>(updatedRestaurant, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRestaurant(@PathVariable Long id) {
        String response = restaurantService.deleteRestaurant(id);
        return new ResponseEntity<>(response,HttpStatus.NO_CONTENT);
    }

    @GetMapping("/findByLocation")
    public ResponseEntity<RestaurantDto> getRestaurantByLocationId(@RequestParam Long locationId) {
        RestaurantDto restaurantDTO = restaurantService.getRestaurantByLocationId(locationId);
        if (restaurantDTO != null) {
            return ResponseEntity.ok(restaurantDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
