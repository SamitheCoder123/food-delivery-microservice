package com.swiggy.app.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Locationdto {

    private Long id;
    private Double latitude;
    private Double longitude;
    private String address;
    private String city;
    private String restaurantName;
    private String zipCode;
}
