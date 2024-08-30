package com.swiggy.app.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiggy.app.demo.Dto.OrderDto;
import com.swiggy.app.demo.Dto.OrderItemDto;
import com.swiggy.app.demo.Exception.ResourceNotFoundException;
import com.swiggy.app.demo.entity.Order;
import com.swiggy.app.demo.entity.OrderItem;
import com.swiggy.app.demo.repository.OrderRepo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepo orderRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public OrderDto createOrder(OrderDto orderDTO) {
        if (orderDTO == null) {
            throw new IllegalArgumentException("OrderDto cannot be null");
        }

        Order order = objectMapper.convertValue(orderDTO, Order.class);

        // Automatically handle the order items
        processOrderItems(order, orderDTO.getItems());

        // Automatically calculate the total amount
        calculateTotalAmount(order);

        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        order.setStatus("CART");

        order = orderRepository.save(order);

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

        // Update the order fields
        order.setStatus(orderDTO.getStatus());

        // Process the updated order items
        processOrderItems(order, orderDTO.getItems());

        // Automatically recalculate the total amount
        calculateTotalAmount(order);

        order.setUpdatedAt(LocalDateTime.now());

        order = orderRepository.save(order);

        return objectMapper.convertValue(order, OrderDto.class);
    }

    private void processOrderItems(Order order, List<OrderItemDto> itemDtos) {
        if (itemDtos == null) {
            return;
        }

        // Clear existing items and update with new ones
        order.getItems().clear();

        for (OrderItemDto itemDto : itemDtos) {
            OrderItem orderItem = objectMapper.convertValue(itemDto, OrderItem.class);
            orderItem.setOrder(order);

            // Check if the item already exists in the cart
            OrderItem existingItem = findExistingItem(order, itemDto.getFoodItemId());

            if (existingItem != null) {
                // Update quantity and price if the item exists
                existingItem.setQuantity(existingItem.getQuantity() + orderItem.getQuantity());
                existingItem.setPrice(itemDto.getPrice());
            } else {
                order.getItems().add(orderItem);
            }
        }
    }

    private OrderItem findExistingItem(Order order, Long foodItemId) {
        return order.getItems().stream()
                .filter(item -> item.getFoodItemId().equals(foodItemId))
                .findFirst()
                .orElse(null);
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

    @Override
    public String getPaymentPage(Map<String, Object> request) {
        String restaurantServiceUrl = "http://localhost:8005/api/payments/cards";
        return restTemplate.postForObject(restaurantServiceUrl, request, String.class);
    }
}
