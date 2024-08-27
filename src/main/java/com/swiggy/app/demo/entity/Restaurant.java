package com.swiggy.app.demo.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * @author
 **/
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
   // @JsonIgnore
    private List<Menu> menus;

    @Column(name = "resto_location_id", nullable = false)
    @JsonProperty("restoLocationId")
    private int restoLocationId;
}