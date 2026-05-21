package com.lumiere.jewelryapi.dto;

import com.lumiere.jewelryapi.entity.CartItem;

public record CartItemResponse(
        Long id,
        Long productId,
        String productName,
        String productImageUrl,
        Double productPrice,
        Integer quantity,
        Double subtotal
) {
    public static CartItemResponse from(CartItem item) {
        return new CartItemResponse(
                item.getId(),
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getProduct().getImageUrl(),
                item.getProduct().getPrice(),
                item.getQuantity(),
                item.getProduct().getPrice() * item.getQuantity()
        );
    }
}