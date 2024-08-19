package com.swiggy.app.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiggy.app.demo.Dto.OrderDto;
import com.swiggy.app.demo.Exception.ResourceNotFoundException;
import com.swiggy.app.demo.entity.Order;
import com.swiggy.app.demo.entity.OrderItem;
import com.swiggy.app.demo.repository.OrderRepo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepo orderRepository;  // Use the correct instance variable name here

    @Autowired
    private ObjectMapper objectMapper; // Inject ObjectMapper

    @Override
    public OrderDto createOrder(OrderDto orderDTO) {
        if (orderDTO == null) {
            throw new IllegalArgumentException("OrderDto cannot be null");
        }

        // Convert DTO to entity
        Order order = objectMapper.convertValue(orderDTO, Order.class);
        // Handle OrderItems if necessary
        if (order.getItems() != null) {
            for (OrderItem item : order.getItems()) {
                item.setOrder(order); // Set the order reference in each item
            }
        }
        // Set additional fields if necessary
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        // Save the order entity
        order = orderRepository.save(order);

        // Convert entity back to DTO
        return objectMapper.convertValue(order, OrderDto.class);
    }

    @Override
    public OrderDto getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return objectMapper.convertValue(order, OrderDto.class);
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(order -> objectMapper.convertValue(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto updateOrder(Long id, OrderDto orderDTO) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        // Update order fields from DTO
        order.setTotalAmount(orderDTO.getTotalAmount());
        order.setStatus(orderDTO.getStatus());
        order.setUpdatedAt(LocalDateTime.now());

        // Save updated order
        order = orderRepository.save(order);

        return objectMapper.convertValue(order, OrderDto.class);
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        orderRepository.delete(order);
    }

}
