package com.lumiere.jewelryapi.dto;

import com.lumiere.jewelryapi.entity.Order;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long id,
        Double totalAmount,
        String status,
        LocalDateTime createdAt,
        List<OrderItemResponse> items
) {
    public static OrderResponse from(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getCreatedAt(),
                order.getItems().stream()
                        .map(OrderItemResponse::from)
                        .toList()
        );
    }
}