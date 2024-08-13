package com.swiggy.app.demo.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrackingDTO {
    private Long id;
    private Long orderId;
    private String status;
    private LocalDateTime timestamp;
}
