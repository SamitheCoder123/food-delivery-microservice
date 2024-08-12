package com.swiggy.app.demo.service;

import com.swiggy.app.demo.Dto.OrderDto;
import com.swiggy.app.demo.Exception.ResourceNotFoundException;
import com.swiggy.app.demo.entity.Order;
import com.swiggy.app.demo.repository.OrderRepo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepo orderRepository;  // Use the correct instance variable name here

    @Override
    public OrderDto createOrder(OrderDto orderDTO) {
        Order order = convertToEntity(orderDTO);
        order = orderRepository.save(order);  // Use the instance variable here
        return convertToDTO(order);
    }

    @Override
    public OrderDto getOrderById(Long id) {
        Order order = orderRepository.findById(id)  // Use the instance variable here
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        return convertToDTO(order);
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();  // Use the instance variable here
        return orders.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public OrderDto updateOrder(Long id, OrderDto orderDTO) {
        Order order = orderRepository.findById(id)  // Use the instance variable here
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        order.setTotalAmount(orderDTO.getTotalAmount());
        order.setStatus(orderDTO.getStatus());
        order.setUpdatedAt(orderDTO.getUpdatedAt());
        order = orderRepository.save(order);  // Use the instance variable here
        return convertToDTO(order);
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)  // Use the instance variable here
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        orderRepository.delete(order);  // Use the instance variable here
    }

    private Order convertToEntity(OrderDto orderDTO) {
        return new Order(orderDTO.getUserId(), orderDTO.getTotalAmount(), orderDTO.getStatus());
    }

    private OrderDto convertToDTO(Order order) {
        return new OrderDto(order.getId(), order.getUserId(), order.getTotalAmount(), order.getStatus(), order.getCreatedAt(), order.getUpdatedAt());
    }
}
