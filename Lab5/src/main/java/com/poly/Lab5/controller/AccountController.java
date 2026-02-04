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

    @GetMapping("/account/register")
    public String register() {
        return "account/register";
    }

    @PostMapping("/account/register")
    public String registerSubmit(Model model) {
        String username = paramService.getString("username", "");
        String password = paramService.getString("password", "");
        String fullname = paramService.getString("fullname", "");
        String email = paramService.getString("email", "");
        String photo = paramService.save("photo", "uploads");

        if (username.isBlank() || password.isBlank() || fullname.isBlank() || email.isBlank()) {
            model.addAttribute("message", "Vui lòng nhập đầy đủ thông tin.");
            model.addAttribute("messageType", "error");
            return "account/register";
        }

        if (photo.isBlank()) {
            model.addAttribute("message", "Vui lòng chọn hình ảnh đại diện.");
            model.addAttribute("messageType", "error");
            return "account/register";
        }

        model.addAttribute("message", "Đăng ký thành công.");
        model.addAttribute("messageType", "success");
        model.addAttribute("photoName", photo);
        return "account/register";
    }
}
