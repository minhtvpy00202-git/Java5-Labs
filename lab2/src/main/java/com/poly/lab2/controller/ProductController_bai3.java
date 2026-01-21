package com.poly.lab2.controller;

import com.poly.lab2.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductController_bai3 {
    @GetMapping("/product/form")
    public String form(Model model) {
        model.addAttribute("product", new Product());
        return "/View/form";
    }

    @PostMapping("/product/save")
    public String save(Product product, Model model) {
        model.addAttribute("product", product);
        return "/View/form";
    }
}

