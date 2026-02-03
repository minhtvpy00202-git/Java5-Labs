package com.poly.lab6.controller;

import com.poly.lab6.dao.ProductDAO;
import com.poly.lab6.dao.CategoryDAO;
import com.poly.lab6.entity.Product;
import com.poly.lab6.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;

import java.util.Optional;

@Controller
public class ProductController {

    @Autowired
    ProductDAO dao;
    @Autowired
    CategoryDAO categoryDAO;

    // SORT
    @RequestMapping("/product/sort")
    public String sort(Model model,
                       @RequestParam("field") Optional<String> field) {

        Sort sort = Sort.by(Sort.Direction.DESC,
                field.orElse("price"));

        model.addAttribute("field", field.orElse("price").toUpperCase());
        model.addAttribute("items", dao.findAll(sort));
        return "product/sort";
    }

    // PAGINATION
    @RequestMapping("/product/page")
    public String page(Model model,
                       @RequestParam("p") Optional<Integer> p,
                       @RequestParam("q") Optional<String> q,
                       @RequestParam("size") Optional<Integer> size,
                       @RequestParam("sort") Optional<String> sortField,
                       @RequestParam("dir") Optional<String> dir,
                       @RequestParam("id") Optional<Integer> id) {

        String field = sortField.orElse("id");
        Sort.Direction direction = "desc".equalsIgnoreCase(dir.orElse("asc")) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(p.orElse(0), size.orElse(5), Sort.by(direction, field));
        Page<Product> page = q.filter(s -> !s.isBlank())
                .map(s -> dao.findByNameContaining(s, pageable))
                .orElseGet(() -> dao.findAll(pageable));

        model.addAttribute("page", page);
        model.addAttribute("q", q.orElse(""));
        model.addAttribute("size", size.orElse(5));
        model.addAttribute("sort", field);
        model.addAttribute("dir", direction.isAscending() ? "asc" : "desc");
        Product item = id.flatMap(dao::findById).orElseGet(() -> {
            Product pdt = new Product();
            pdt.setAvailable(true);
            pdt.setCategory(new Category());
            return pdt;
        });
        model.addAttribute("item", item);
        model.addAttribute("categories", categoryDAO.findAll());
        return "product/page";
    }

    @RequestMapping("/product/create")
    public String create(@Valid Product item, BindingResult result, Model model, RedirectAttributes ra) {
        if (item.getCategory() != null && item.getCategory().getId() != null) {
            categoryDAO.findById(item.getCategory().getId()).ifPresent(item::setCategory);
        }
        if (result.hasErrors()) {
            String field = "id";
            Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, field));
            Page<Product> page = dao.findAll(pageable);
            model.addAttribute("page", page);
            model.addAttribute("q", "");
            model.addAttribute("size", 5);
            model.addAttribute("sort", field);
            model.addAttribute("dir", "asc");
            model.addAttribute("categories", categoryDAO.findAll());
            return "product/page";
        }
        dao.save(item);
        ra.addFlashAttribute("message", "Tạo Product thành công");
        return "redirect:/product/page";
    }

    @RequestMapping("/product/update")
    public String update(@Valid Product item, BindingResult result, Model model, RedirectAttributes ra) {
        if (item.getCategory() != null && item.getCategory().getId() != null) {
            categoryDAO.findById(item.getCategory().getId()).ifPresent(item::setCategory);
        }
        if (result.hasErrors()) {
            String field = "id";
            Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, field));
            Page<Product> page = dao.findAll(pageable);
            model.addAttribute("page", page);
            model.addAttribute("q", "");
            model.addAttribute("size", 5);
            model.addAttribute("sort", field);
            model.addAttribute("dir", "asc");
            model.addAttribute("categories", categoryDAO.findAll());
            return "product/page";
        }
        dao.save(item);
        ra.addFlashAttribute("message", "Cập nhật Product thành công");
        return "redirect:/product/page?id=" + item.getId();
    }

    @RequestMapping("/product/delete/{id}")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes ra) {
        dao.deleteById(id);
        ra.addFlashAttribute("message", "Xóa Product thành công");
        return "redirect:/product/page";
    }
}
