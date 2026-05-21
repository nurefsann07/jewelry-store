package com.lumiere.jewelryapi.service;

import com.lumiere.jewelryapi.dto.OrderResponse;
import com.lumiere.jewelryapi.entity.CartItem;
import com.lumiere.jewelryapi.entity.Order;
import com.lumiere.jewelryapi.entity.OrderItem;
import com.lumiere.jewelryapi.entity.User;
import com.lumiere.jewelryapi.repository.CartItemRepository;
import com.lumiere.jewelryapi.repository.OrderRepository;
import com.lumiere.jewelryapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    public OrderResponse checkout(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        List<CartItem> cartItems = cartItemRepository.findByUserOrderByAddedAtDesc(user);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Sepetiniz boş");
        }

        // Toplam hesapla
        double total = cartItems.stream()
                .mapToDouble(ci -> ci.getProduct().getPrice() * ci.getQuantity())
                .sum();

        // Order'ı oluştur
        Order order = Order.builder()
                .user(user)
                .totalAmount(total)
                .status("CONFIRMED")
                .build();

        // CartItem'ları OrderItem'a çevir (fiyatı sabitle)
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem ci : cartItems) {
            OrderItem oi = OrderItem.builder()
                    .order(order)
                    .product(ci.getProduct())
                    .quantity(ci.getQuantity())
                    .priceAtPurchase(ci.getProduct().getPrice())
                    .build();
            orderItems.add(oi);
        }
        order.setItems(orderItems);

        Order saved = orderRepository.save(order);

        // Sepeti temizle
        cartItemRepository.deleteByUser(user);

        return OrderResponse.from(saved);
    }

    public List<OrderResponse> getMyOrders(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
        return orderRepository.findByUserOrderByCreatedAtDesc(user).stream()
                .map(OrderResponse::from)
                .toList();
    }
}