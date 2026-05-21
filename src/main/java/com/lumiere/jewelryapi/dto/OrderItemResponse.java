package com.lumiere.jewelryapi.dto;

import com.lumiere.jewelryapi.entity.OrderItem;

public record OrderItemResponse(
        Long id,
        Long productId,
        String productName,
        String productImageUrl,
        Integer quantity,
        Double priceAtPurchase,
        Double subtotal
) {
    public static OrderItemResponse from(OrderItem item) {
        return new OrderItemResponse(
                item.getId(),
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getProduct().getImageUrl(),
                item.getQuantity(),
                item.getPriceAtPurchase(),
                item.getPriceAtPurchase() * item.getQuantity()
        );
    }
}