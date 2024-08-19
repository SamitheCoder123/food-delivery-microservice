package com.swiggy.app.demo.repository;

import com.swiggy.app.demo.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author
 **/
public interface LocationRepo extends JpaRepository<Location, Long> {
    
}
