package com.poly.lab7.dao;

import com.poly.lab7.entity.Product;
import com.poly.lab7.report.Report;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductDAO extends JpaRepository<Product, Integer> {

    // ===== BÀI 1: @Query tìm theo giá =====
    @Query("FROM Product o WHERE o.price BETWEEN ?1 AND ?2")
    List<Product> findByPrice(double minPrice, double maxPrice);

    // ===== BÀI 2: @Query + phân trang =====
    @Query("FROM Product o WHERE o.name LIKE ?1")
    Page<Product> findByKeywords(String keywords, Pageable pageable);

    // ===== BÀI 3: Truy vấn tổng hợp =====
    @Query("""
        SELECT o.category AS group,
               SUM(o.price) AS sum,
               COUNT(o) AS count
        FROM Product o
        GROUP BY o.category
        ORDER BY SUM(o.price) DESC
    """)
    List<Report> getInventoryByCategory();

    // ===== PHẦN II – DSL =====

    // Bài 4
    List<Product> findByPriceBetween(double minPrice, double maxPrice);

    // Bài 4 (Sort)
    List<Product> findByPriceBetween(double minPrice, double maxPrice, Sort sort);

    // Bài 5
    Page<Product> findAllByNameLike(String keywords, Pageable pageable);
}
