package com.swiggy.app.userService.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @Id
    private int id;
    private String username;
    private String password;
    private String email;
   // private ArrayList<Addresses> address;
   private String address;
    private long number;

}
