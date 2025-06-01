package com.drsimple.springbootmongoapp.service;

import com.drsimple.springbootmongoapp.dtos.OrderRequestDTO;
import com.drsimple.springbootmongoapp.dtos.OrderResponseDTO;
import com.drsimple.springbootmongoapp.exceptions.ResourceNotFoundException;
import com.drsimple.springbootmongoapp.model.Order;
import com.drsimple.springbootmongoapp.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderResponseDTO createOrder(OrderRequestDTO request) {
        Order order = Order.builder()
                .product(request.getProduct())
                .price(request.getPrice())
                .userId(request.getUserId())
                .build();

        return toResponseDTO(orderRepository.save(order));
    }

    public OrderResponseDTO getOrderById(String id) {
        return orderRepository.findById(id)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    public List<OrderResponseDTO> getOrdersByUserId(String userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public void deleteOrder(String id) {
        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Order not found");
        }
        orderRepository.deleteById(id);
    }

    private OrderResponseDTO toResponseDTO(Order order) {
        return OrderResponseDTO.builder()
                .id(order.getId())
                .product(order.getProduct())
                .price(order.getPrice())
                .userId(order.getUserId())
                .build();
    }
}