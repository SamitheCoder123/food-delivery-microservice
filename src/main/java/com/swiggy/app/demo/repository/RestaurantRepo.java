package com.swiggy.app.demo.repository;

import com.swiggy.app.demo.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *@author
 **/

@Repository
public interface RestaurantRepo extends JpaRepository<Restaurant, Long> {
    @Query(value = "SELECT * FROM restaurants WHERE location_id = :locationId", nativeQuery = true)
    Restaurant findByLocationId(@Param("locationId") long locationId);
}