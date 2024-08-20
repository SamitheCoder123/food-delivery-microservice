package com.swiggy.app.demo.service;

import com.swiggy.app.demo.entity.Location;
import com.swiggy.app.demo.repository.LocationRepo;
import com.swiggy.app.demo.util.LocationServiceUtility;
import org.hibernate.annotations.SecondaryRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author
 **/
@Service
public class LocationServiceImpl implements LocationService  {

    @Autowired
    private LocationRepo locationRepository;

    @Autowired
    private  LocationServiceUtility locationServiceUtility;

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

    public void loadLocations() {
        locationServiceUtility.loadLocationsToDB();
    }
}
