package com.swiggy.app.demo.repository;

import com.swiggy.app.demo.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author
 **/
@Repository
public interface MenuRepo extends JpaRepository<Menu, Long> {
}