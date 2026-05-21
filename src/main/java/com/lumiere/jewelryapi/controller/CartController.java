package com.lumiere.jewelryapi.controller;

import com.lumiere.jewelryapi.dto.AddToCartRequest;
import com.lumiere.jewelryapi.dto.CartItemResponse;
import com.lumiere.jewelryapi.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public List<CartItemResponse> getCart(Authentication authentication) {
        return cartService.getCart(authentication.getName());
    }

    @PostMapping("/add")
    public CartItemResponse addToCart(@RequestBody AddToCartRequest request, Authentication authentication) {
        return cartService.addToCart(authentication.getName(), request);
    }

    @PutMapping("/items/{id}")
    public ResponseEntity<CartItemResponse> updateQuantity(
            @PathVariable Long id,
            @RequestBody Map<String, Integer> body,
            Authentication authentication) {
        CartItemResponse result = cartService.updateQuantity(authentication.getName(), id, body.get("quantity"));
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.noContent().build();
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Long id, Authentication authentication) {
        cartService.removeFromCart(authentication.getName(), id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart(Authentication authentication) {
        cartService.clearCart(authentication.getName());
        return ResponseEntity.noContent().build();
    }
}