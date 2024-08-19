package com.swiggy.app.demo.runner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiggy.app.demo.entity.Location;
import com.swiggy.app.demo.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author
 **/
public class DataLoader implements CommandLineRunner {

    @Autowired
    private LocationService locationService;

    @Override
    public void run(String... args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Location> locations = objectMapper.readValue(new File("src/main/resources/locations.json"),
                objectMapper.getTypeFactory().constructCollectionType(List.class, Location.class));
        for (Location location : locations) {
            locationService.saveLocation(location);
        }
    }
}
