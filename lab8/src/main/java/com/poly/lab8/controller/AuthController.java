package com.poly.lab8.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.poly.lab8.entity.Account;
import com.poly.lab8.service.AccountService;

@Controller
public class AuthController {

    @Autowired
    AccountService accountService;

    @Autowired
    HttpSession session;

    @GetMapping("/auth/login")
    public String loginForm() {
        return "auth/login";
    }

    @PostMapping("/auth/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            Model model) {

        Account user = accountService.findById(username);

        if (user == null) {
            model.addAttribute("message", "Sai tài khoản");
            return "auth/login";
        }
        if (!user.getPassword().equals(password)) {
            model.addAttribute("message", "Sai mật khẩu");
            return "auth/login";
        }

        session.setAttribute("user", user);

        String uri = (String) session.getAttribute("securityUri");
        return (uri != null) ? "redirect:" + uri : "redirect:/";
    }

    @GetMapping("/auth/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

}
