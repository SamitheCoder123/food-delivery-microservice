package com.swiggy.app.demo.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestaurantDto {
    private Long id;                   // Unique identifier for the restaurant
    private String name;               // Name of the restaurant
    private String location;           // Location of the restaurant
    private List<MenuDto> menus;       // List of menus associated with this restaurant
}