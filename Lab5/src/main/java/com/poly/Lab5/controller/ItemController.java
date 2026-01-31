package com.poly.Lab5.controller;

import com.poly.Lab5.db.DB;
import com.poly.Lab5.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ItemController {

    @Autowired
    ShoppingCartService cart;

    @RequestMapping("/item/index")
    public String list(Model model) {
        model.addAttribute("items", DB.items.values());
        model.addAttribute("cart", cart);
        return "item/index";
    }
}
