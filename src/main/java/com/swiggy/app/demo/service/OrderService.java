package com.swiggy.app.demo.service;

import com.swiggy.app.demo.Dto.OrderDto;

import java.util.List;

/**
 * @author
 **/
public interface OrderService {
    OrderDto createOrder(OrderDto orderDTO);
    OrderDto getOrderById(Long id);
    List<OrderDto> getAllOrders();
    OrderDto updateOrder(Long id, OrderDto orderDTO);
    void deleteOrder(Long id);
}
