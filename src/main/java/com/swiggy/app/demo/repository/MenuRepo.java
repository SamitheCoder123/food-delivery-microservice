package com.swiggy.app.demo.repository;

import com.swiggy.app.demo.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author
 **/
@Repository
public interface MenuRepo extends JpaRepository<Menu, Long> {
    // JPQL query to find menus by restaurant ID
    @Query("SELECT m FROM Menu m WHERE m.restaurant.id = :restaurantId")
    List<Menu> findByRestaurantId(Long restaurantId);

}