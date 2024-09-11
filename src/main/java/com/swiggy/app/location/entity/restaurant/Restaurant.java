package com.swiggy.app.location.entity.restaurant;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.swiggy.app.location.entity.Location;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * @author : Samiullah Makandar
 * @purpose :
 */
@Entity
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "restaurants")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "location", nullable = false)
    private String location;


    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Menu> menus;

    @OneToOne
    @JoinColumn(name = "location_id", nullable = false)  // Ensure nullable is false if this is a required field
    private Location locationId;

}