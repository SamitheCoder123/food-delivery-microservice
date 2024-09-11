package com.swiggy.app.location.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiggy.app.location.entity.Location;
import com.swiggy.app.location.entity.restaurant.Restaurant;
import com.swiggy.app.location.exception.ResourceNotFoundException;
import com.swiggy.app.location.repository.LocationRepo;
import com.swiggy.app.location.util.LocationServiceUtility;
import com.fasterxml.jackson.core.type.TypeReference;
import org.hibernate.mapping.Map;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


import java.util.List;
import java.util.Optional;

/**
 * @author
 **/
@Service
public class LocationServiceImpl implements LocationService  {

    String url="http://localhost:8003/api/restaurants/{id}";

    @Autowired
    private LocationRepo locationRepository;

    @Autowired
    private  LocationServiceUtility locationServiceUtility;

    @Autowired
    private  RestTemplate restTemplate;
/*

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }*/


    @Override
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    @Override
    public Optional<Location> getLocationById(Long id) {
        return locationRepository.findById(id);
    }

    @Override
    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }

    @Override
    public void deleteLocation(Long id) {
        locationRepository.deleteById(id);
    }

    public ResponseEntity<String> loadLocations() throws JsonProcessingException {
        /*List<Location> locations = locationServiceUtility.loadLocationsToDB();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class, locations);
        return response;*/
        /*Object locations = locationServiceUtility.loadLocationsToDB();
        ResponseEntity<String> response = restTemplate.postForEntity(url, locations, String.class);
        return response;*/
        Object locations = locationServiceUtility.loadLocationsToDB();
        if (locations instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) locations;
            ObjectMapper objectMapper = new ObjectMapper();
            java.util.Map<String, Object> map = objectMapper.readValue(jsonObject.toString(), new TypeReference<java.util.Map<String, Object>>() {});

            locations = map;
        }
        // Convert locations to JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(locations);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(jsonString, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        return response;
    }

    @Override
    public ResponseEntity<String> findByLocationId(Long id) {
        Optional<Location> byId = locationRepository.findById(id);
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url, byId.get(), String.class);
        return stringResponseEntity;
    }

    public Location findById(Long id) {
        return locationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Location not found"));
    }

    public Restaurant getRestaurantByLocation(Long locationId) {
        String restaurantServiceUrl = "http://localhost:8003/api/restaurants/findByLocation?locationId=" + locationId;
        return restTemplate.getForObject(restaurantServiceUrl, Restaurant.class);
    }

}
