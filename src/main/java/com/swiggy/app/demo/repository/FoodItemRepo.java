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

    // Custom query to find food items by menu ID
    List<FoodItem> findByMenuId(Long menuId);
}