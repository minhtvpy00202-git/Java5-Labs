package com.poly.lab2.controller;

import com.poly.lab2.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class ProductController_bai4 {

    @GetMapping("/product/form1")
    public String form(Model model) {
        Product p = new Product();
        p.setName("iPhone 30");
        p.setPrice(5000.0);

        model.addAttribute("p", p);

        model.addAttribute("items", getItems());

        return "product/form";
    }

    @PostMapping("/product/save1")
    public String save(
            @ModelAttribute("p") Product p,
            Model model) {

        model.addAttribute("p", p);

        model.addAttribute("items", getItems());

        return "product/form";
    }

    // ?3
    @ModelAttribute("items")
    public List<Product> getItems() {
        return Arrays.asList(
                new Product("A", 1.0),
                new Product("B", 12.0)
        );
    }
}
