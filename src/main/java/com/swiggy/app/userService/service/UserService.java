package com.swiggy.app.userService.service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiggy.app.userService.dto.UserDto;
import com.swiggy.app.userService.model.Users;
import com.swiggy.app.userService.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private UserRepo repo;

    @Autowired
    private ObjectMapper objectMapper;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Users register(Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        repo.save(user);
        return user;
    }

    public String verify(Users user) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername());
        } else {
            return "fail";
        }
    }

    public UserDto updateUser(int id, UserDto userDto) throws JsonMappingException {
        Optional<Users> usersOptional = repo.findById(id);

        if (usersOptional.isPresent()) {
            Users users = usersOptional.get();
            objectMapper.updateValue(users, userDto); // Update entity with DTO values
            if (userDto.getPassword() != null) {
                users.setPassword(encoder.encode(userDto.getPassword()));
            }
            users = repo.save(users);
            return objectMapper.convertValue(users, UserDto.class); // Convert entity back to DTO
        } else {
            throw new RuntimeException("User not found with ID: " + id);
        }
    }
}
