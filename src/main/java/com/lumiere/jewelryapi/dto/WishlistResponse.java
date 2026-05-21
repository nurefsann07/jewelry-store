package com.lumiere.jewelryapi.dto;

import com.lumiere.jewelryapi.entity.WishlistItem;

public record WishlistResponse(
        Long id,
        Long productId,
        String productName,
        String productImageUrl,
        Double productPrice,
        String productCategory,
        String productTag
) {
    public static WishlistResponse from(WishlistItem item) {
        return new WishlistResponse(
                item.getId(),
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getProduct().getImageUrl(),
                item.getProduct().getPrice(),
                item.getProduct().getCategory(),
                item.getProduct().getTag()
        );
    }
}