package com.swiggy.app.location.repository;

import com.swiggy.app.location.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author
 **/
public interface LocationRepo extends JpaRepository<Location, Long> {
    
}
