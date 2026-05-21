package com.lumiere.jewelryapi.repository;

import com.lumiere.jewelryapi.entity.Product;
import com.lumiere.jewelryapi.entity.User;
import com.lumiere.jewelryapi.entity.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {
    List<WishlistItem> findByUserOrderByAddedAtDesc(User user);
    Optional<WishlistItem> findByUserAndProduct(User user, Product product);
    boolean existsByUserAndProduct(User user, Product product);
}