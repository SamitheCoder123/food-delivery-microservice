package com.swiggy.app.userService.repository;

import com.swiggy.app.userService.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : Samiullah Makandar
 * @purpose : repository
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
