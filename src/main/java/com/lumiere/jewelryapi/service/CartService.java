package com.lumiere.jewelryapi.service;

import com.lumiere.jewelryapi.dto.AddToCartRequest;
import com.lumiere.jewelryapi.dto.CartItemResponse;
import com.lumiere.jewelryapi.entity.CartItem;
import com.lumiere.jewelryapi.entity.Product;
import com.lumiere.jewelryapi.entity.User;
import com.lumiere.jewelryapi.repository.CartItemRepository;
import com.lumiere.jewelryapi.repository.ProductRepository;
import com.lumiere.jewelryapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public List<CartItemResponse> getCart(String email) {
        User user = getUser(email);
        return cartItemRepository.findByUserOrderByAddedAtDesc(user).stream()
                .map(CartItemResponse::from)
                .toList();
    }

    public CartItemResponse addToCart(String email, AddToCartRequest request) {
        User user = getUser(email);
        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new RuntimeException("Ürün bulunamadı"));

        int qty = (request.quantity() == null || request.quantity() < 1) ? 1 : request.quantity();

        CartItem item = cartItemRepository.findByUserAndProduct(user, product)
                .map(existing -> {
                    existing.setQuantity(existing.getQuantity() + qty);
                    return existing;
                })
                .orElseGet(() -> CartItem.builder()
                        .user(user)
                        .product(product)
                        .quantity(qty)
                        .build());

        return CartItemResponse.from(cartItemRepository.save(item));
    }

    public CartItemResponse updateQuantity(String email, Long itemId, Integer quantity) {
        User user = getUser(email);
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Sepet öğesi bulunamadı"));
        if (!item.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Yetkisiz erişim");
        }
        if (quantity == null || quantity <= 0) {
            cartItemRepository.delete(item);
            return null;
        }
        item.setQuantity(quantity);
        return CartItemResponse.from(cartItemRepository.save(item));
    }

    public void removeFromCart(String email, Long itemId) {
        User user = getUser(email);
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Sepet öğesi bulunamadı"));
        if (!item.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Yetkisiz erişim");
        }
        cartItemRepository.delete(item);
    }

    public void clearCart(String email) {
        User user = getUser(email);
        cartItemRepository.deleteByUser(user);
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
    }
}