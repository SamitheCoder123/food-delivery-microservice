package com.swiggy.app.demo.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private long id;
    private Long userId;
    private Double totalAmount;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
