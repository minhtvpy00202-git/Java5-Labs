package com.poly.Lab5.controller;


import com.poly.Lab5.service.CookieService;
import com.poly.Lab5.service.ParamService;
import com.poly.Lab5.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

@Controller
public class AccountController {

    @Autowired
    ParamService paramService;

    @Autowired
    CookieService cookieService;

    @Autowired
    SessionService sessionService;

    @GetMapping("/account/login")
    public String login1(Model model) {
        String savedUser = cookieService.getValue("user");
        model.addAttribute("savedUser", savedUser);
        return "account/login";
    }

    @PostMapping("/account/login")
    public String login2() {
        return "account/login";
    }
}
