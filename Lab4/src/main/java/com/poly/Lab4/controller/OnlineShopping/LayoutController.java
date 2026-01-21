package com.poly.Lab4.controller.OnlineShopping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LayoutController {

    @GetMapping("/home/index")
    public String index() {
        return "WebLayout/home";
    }

    @GetMapping("/home/about")
    public String about() {
        return "WebLayout/about";
    }
}
