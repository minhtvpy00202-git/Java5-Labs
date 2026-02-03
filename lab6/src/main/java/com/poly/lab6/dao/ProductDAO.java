package com.poly.lab6.dao;

import com.poly.lab6.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductDAO extends JpaRepository<Product, Integer> {
    Page<Product> findByNameContaining(String name, Pageable pageable);
}
