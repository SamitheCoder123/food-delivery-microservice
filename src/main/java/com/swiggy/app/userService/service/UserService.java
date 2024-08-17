package com.swiggy.app.demo.service;

import com.swiggy.app.demo.entity.User;
import org.springframework.stereotype.Service;

/**
 * @author : Samiullah Makandar
 * @purpose : service interface
 */
public interface UserService {
    public void registerUser(User user);
    User findByUsername(String username);
}
