package com.poly.lab6.controller;

import com.poly.lab6.dao.CategoryDAO;
import com.poly.lab6.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;

@Controller
public class CategoryController {

    @Autowired
    CategoryDAO dao;

    @RequestMapping("/category/index")
    public String index(Model model) {
        model.addAttribute("item", new Category());
        model.addAttribute("items", dao.findAll());
        return "category/index";
    }

    @RequestMapping("/category/edit/{id}")
    public String edit(Model model, @PathVariable("id") String id, RedirectAttributes ra) {
        Category item = dao.findById(id).orElse(null);
        if (item == null) {
            ra.addFlashAttribute("error", "Không tìm thấy Category: " + id);
            return "redirect:/category/index";
        }
        model.addAttribute("item", item);
        model.addAttribute("items", dao.findAll());
        return "category/index";
    }

    @RequestMapping("/category/create")
    public String create(@Valid Category item, BindingResult result, Model model, RedirectAttributes ra) {
        if (result.hasErrors()) {
            model.addAttribute("items", dao.findAll());
            return "category/index";
        }
        dao.save(item);
        ra.addFlashAttribute("message", "Tạo Category thành công");
        return "redirect:/category/index";
    }

    @RequestMapping("/category/update")
    public String update(@Valid Category item, BindingResult result, Model model, RedirectAttributes ra) {
        if (result.hasErrors()) {
            model.addAttribute("items", dao.findAll());
            return "category/index";
        }
        dao.save(item);
        ra.addFlashAttribute("message", "Cập nhật Category thành công");
        return "redirect:/category/edit/" + item.getId();
    }

    @RequestMapping("/category/delete/{id}")
    public String delete(@PathVariable("id") String id, RedirectAttributes ra) {
        dao.deleteById(id);
        ra.addFlashAttribute("message", "Xóa Category thành công");
        return "redirect:/category/index";
    }
}
