package poly.edu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {
    @RequestMapping("/poly/hello")
    public String sayHello(Model model) {
        model.addAttribute("title", "Java 5");
        model.addAttribute("subject", "Lab 1");
        model.addAttribute("content", "Đây là bài lab 1");
        return "hello";
    }
}
