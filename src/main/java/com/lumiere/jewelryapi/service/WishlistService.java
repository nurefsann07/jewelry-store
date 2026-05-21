package com.lumiere.jewelryapi.service;

import com.lumiere.jewelryapi.dto.WishlistResponse;
import com.lumiere.jewelryapi.entity.Product;
import com.lumiere.jewelryapi.entity.User;
import com.lumiere.jewelryapi.entity.WishlistItem;
import com.lumiere.jewelryapi.repository.ProductRepository;
import com.lumiere.jewelryapi.repository.UserRepository;
import com.lumiere.jewelryapi.repository.WishlistItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class WishlistService {

    private final WishlistItemRepository wishlistRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public List<WishlistResponse> getWishlist(String email) {
        User user = getUser(email);
        return wishlistRepository.findByUserOrderByAddedAtDesc(user).stream()
                .map(WishlistResponse::from)
                .toList();
    }

    public WishlistResponse addToWishlist(String email, Long productId) {
        User user = getUser(email);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Ürün bulunamadı"));

        return wishlistRepository.findByUserAndProduct(user, product)
                .map(WishlistResponse::from)
                .orElseGet(() -> {
                    WishlistItem item = WishlistItem.builder()
                            .user(user)
                            .product(product)
                            .build();
                    return WishlistResponse.from(wishlistRepository.save(item));
                });
    }

    public void removeFromWishlist(String email, Long productId) {
        User user = getUser(email);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Ürün bulunamadı"));
        wishlistRepository.findByUserAndProduct(user, product)
                .ifPresent(wishlistRepository::delete);
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
    }
}