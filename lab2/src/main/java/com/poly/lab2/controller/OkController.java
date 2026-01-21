package com.poly.lab2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/ctrl")
public class OkController {
    @PostMapping(value="/ok", params = "!x")
    public String m1(Model model) {
        model.addAttribute("result", "POST request");
        return "ok";
    }

    @GetMapping("/ok")
    public String m2(Model model) {
        model.addAttribute("result", "GET request");
        return "ok";
    }

    @PostMapping(value="/ok", params="x")
    public String m3 (@RequestParam String x, Model model) {
        model.addAttribute("result", "POST request có tham số x");
        return "ok";
    }
 }
