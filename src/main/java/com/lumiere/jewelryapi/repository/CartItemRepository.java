package com.lumiere.jewelryapi.repository;

import com.lumiere.jewelryapi.entity.CartItem;
import com.lumiere.jewelryapi.entity.Product;
import com.lumiere.jewelryapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUserOrderByAddedAtDesc(User user);
    Optional<CartItem> findByUserAndProduct(User user, Product product);
    void deleteByUser(User user);
}