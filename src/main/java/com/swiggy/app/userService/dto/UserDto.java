package com.swiggy.app.userService.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

/**
 * @author : Samiullah Makandar
 * @purpose :
 */

@Getter
@Setter
public class UserDto {
    private int id;
    private String username;
    private String password;
    private String email;
    //private ArrayList<Addresses> address;
    private String address;
    private long number;

}
