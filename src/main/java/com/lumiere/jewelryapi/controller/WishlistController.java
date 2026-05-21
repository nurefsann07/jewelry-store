package com.lumiere.jewelryapi.controller;

import com.lumiere.jewelryapi.dto.WishlistResponse;
import com.lumiere.jewelryapi.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    @GetMapping
    public List<WishlistResponse> getWishlist(Authentication authentication) {
        return wishlistService.getWishlist(authentication.getName());
    }

    @PostMapping("/{productId}")
    public WishlistResponse addToWishlist(@PathVariable Long productId, Authentication authentication) {
        return wishlistService.addToWishlist(authentication.getName(), productId);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> removeFromWishlist(@PathVariable Long productId, Authentication authentication) {
        wishlistService.removeFromWishlist(authentication.getName(), productId);
        return ResponseEntity.noContent().build();
    }
}