package com.swiggy.app.userService.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.swiggy.app.userService.config.JwtFilter;
import com.swiggy.app.userService.dto.UserDto;
import com.swiggy.app.userService.model.Users;
import com.swiggy.app.userService.service.TokenBlacklistService;
import com.swiggy.app.userService.service.UserService;
//import com.swiggy.app.userService.util.MockHttpServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService service;

    private TokenBlacklistService tokenBlacklistService;

    public UserController(TokenBlacklistService tokenBlacklistService) {
        this.tokenBlacklistService = tokenBlacklistService;
    }

    @PostMapping("/register")
    public Users register(@RequestBody Users user) {
        return service.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody Users user) {
        return service.verify(user);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }
        if (token != null) {
            tokenBlacklistService.addToBlacklist(token);
            return ResponseEntity.ok("Logout successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable int id,@RequestBody UserDto userDto){
        try {
            UserDto updateUsers = service.updateUser(id, userDto);
            return ResponseEntity.ok(updateUsers);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        }
    }

    //tesing purpose
/*    public static void main(String[] args) {
        TokenBlacklistService tokenBlacklistService = new TokenBlacklistService();

        // Instantiate UserController with the initialized service
        UserController userController = new UserController(tokenBlacklistService);

        // Create a mock HttpServletRequest
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiaW5kdSIsImlhdCI6MTcyNDIyNDU3MywiZXhwIjoxNzI0MjI1MTczfQ.7cf2RQ3JK-6f2wdDrIl6NRp5xHLVLKrRzjtMNZxCPgM");

        // Call the logout method
        ResponseEntity<String> response = userController.logout(mockRequest);

        // Print the response
        System.out.println("Response: " + response.getBody());
    }*/
}

