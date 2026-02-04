package com.poly.Lab5.controller;

import com.poly.Lab5.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    ShoppingCartService cart;

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        model.addAttribute("cart", cart);
        return "home";
    }
}
