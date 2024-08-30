package com.swiggy.app.demo.service;

import com.swiggy.app.demo.Dto.OrderDto;

import java.util.List;
import java.util.Map;

/**
 * @author
 **/
public interface OrderService {
    OrderDto createOrder(OrderDto orderDTO);
    OrderDto getOrderById(Long id);
    List<OrderDto> getAllOrders();
    OrderDto updateOrder(Long id, OrderDto orderDTO);
    void deleteOrder(Long id);

    String getPaymentPage(Map<String, Object> request);
}
