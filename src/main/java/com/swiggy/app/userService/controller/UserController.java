package com.swiggy.app.userService.controller;

import com.swiggy.app.userService.entity.User;
import com.swiggy.app.userService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author : Samiullah Makandar
 * @purpose : controller
 */

@RestController
@RequestMapping("/api") // Base URL for the API endpoints
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        userService.registerUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        User existingUser = userService.findByUsername(user.getUsername());
        if (existingUser != null && user.getPassword().equals(existingUser.getPassword())) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout() {
        // This is a placeholder. In a real application, logout would be handled by security mechanisms.
        return ResponseEntity.ok("Logout successful");
    }
}
