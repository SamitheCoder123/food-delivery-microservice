package com.swiggy.app.demo.repository;

import com.swiggy.app.demo.entity.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author
 **/
@Repository
public interface FoodItemRepo extends JpaRepository<FoodItem, Long> {

    List<FoodItem> findByType(String type); // Find food items by type
}