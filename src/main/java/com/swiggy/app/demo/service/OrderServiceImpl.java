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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepo orderRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public OrderDto createOrder(OrderDto orderDTO) {
        if (orderDTO == null) {
            throw new IllegalArgumentException("OrderDto cannot be null");
        }

        Order order = objectMapper.convertValue(orderDTO, Order.class);

        List<OrderItem> orderItems = new ArrayList<>();
        if (orderDTO.getItems() != null) {
            Order finalOrder = order;
            orderItems = orderDTO.getItems().stream()
                    .map(itemDto -> {
                        OrderItem orderItem = objectMapper.convertValue(itemDto, OrderItem.class);
                        orderItem.setOrder(finalOrder);
                        return orderItem;
                    })
                    .collect(Collectors.toList());
        }

        order.setItems(orderItems);

        // Calculate the total amount
        calculateTotalAmount(order);

        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        order = orderRepository.save(order);

        return objectMapper.convertValue(order, OrderDto.class);
    }

    @Override
    public OrderDto getOrderById(Long id) {
        return null;
    }

    @Override
    public List<OrderDto> getAllOrders() {
        return List.of();
    }

    @Override
    public OrderDto updateOrder(Long id, OrderDto orderDTO) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        // Update order fields from DTO
        order.setTotalAmount(orderDTO.getTotalAmount());
        order.setStatus(orderDTO.getStatus());

        // Update or add items
        List<OrderItem> updatedItems = new ArrayList<>();
        if (orderDTO.getItems() != null) {
            Order finalOrder = order;
            updatedItems = orderDTO.getItems().stream()
                    .map(itemDto -> {
                        OrderItem orderItem = objectMapper.convertValue(itemDto, OrderItem.class);
                        orderItem.setOrder(finalOrder);
                        return orderItem;
                    })
                    .collect(Collectors.toList());
        }

        order.getItems().clear();
        order.getItems().addAll(updatedItems);

        // Recalculate the total amount
        calculateTotalAmount(order);

        order.setUpdatedAt(LocalDateTime.now());

        order = orderRepository.save(order);

        return objectMapper.convertValue(order, OrderDto.class);
    }

    private void calculateTotalAmount(Order order) {
        double total = order.getItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
        order.setTotalAmount(total);
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        orderRepository.delete(order);
    }
}
