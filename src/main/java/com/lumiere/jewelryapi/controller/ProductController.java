package com.lumiere.jewelryapi.controller;

import com.lumiere.jewelryapi.entity.Product;
import com.lumiere.jewelryapi.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;

    @GetMapping
    public List<Product> getAll(@RequestParam(required = false) String category) {
        if (category != null && !category.isEmpty()) {
            return productRepository.findByCategoryOrderByPriceAsc(category);
        }
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getOne(@PathVariable Long id) {
        return productRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/categories")
    public List<String> getCategories() {
        return List.of("Rings", "Necklaces", "Earrings", "Bracelets", "Brooches", "Pendants");
    }
}