package com.poly.lab2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ParamController {

    @RequestMapping("/param/form")
    public String form() {
        return "form";
    }

    @RequestMapping("/param/save/{x}")
    public String save(
            @PathVariable("x") String x,   // *?1*
            @RequestParam("y") String y,    // *?2*
            Model model
    ) {
        model.addAttribute("x", x);
        model.addAttribute("y", y);
        return "form";
    }
}

