package com.swiggy.app.demo.repository;

import com.swiggy.app.demo.entity.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author
 **/
@Repository
public interface FoodItemRepo extends JpaRepository<FoodItem, Long> {
}