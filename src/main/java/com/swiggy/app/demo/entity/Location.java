package com.swiggy.app.demo.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * @author
 **/
@Entity
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double latitude;
    private Double longitude;
    private String address;
    private String city;
    private String restaurantName;
    private String zipCode;
}
