package com.swiggy.app.userService.service;

import com.swiggy.app.userService.entity.User;

/**
 * @author : Samiullah Makandar
 * @purpose : service interface
 */
public interface UserService {
    public void registerUser(User user);
    User findByUsername(String username);
}
