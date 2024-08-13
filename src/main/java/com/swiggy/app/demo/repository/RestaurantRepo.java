package com.swiggy.app.demo.repository;

import com.swiggy.app.demo.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *@author
 **/

@Repository
public interface RestaurantRepo extends JpaRepository<Restaurant, Long> {
}