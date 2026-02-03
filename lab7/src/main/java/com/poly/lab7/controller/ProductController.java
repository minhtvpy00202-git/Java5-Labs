package com.poly.lab7.controller;

import com.poly.lab7.dao.ProductDAO;
import com.poly.lab7.entity.Product;
import com.poly.lab7.service.SessionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {

    @Autowired
    ProductDAO dao;

    @Autowired
    SessionService session;

    // ===== BÀI 1: SEARCH BY PRICE =====
    @RequestMapping("/product/search")
    public String search(Model model,
                         @RequestParam("min") Optional<Double> min,
                         @RequestParam("max") Optional<Double> max,
                         @RequestParam("sort") Optional<String> sort,
                         @RequestParam("dir") Optional<String> dir) {

        double minPrice = min.orElse(Double.MIN_VALUE);
        double maxPrice = max.orElse(Double.MAX_VALUE);

        String sortField = sort.orElse("price");
        String sortDir = dir.orElse("asc");

        Sort sortSpec = "desc".equalsIgnoreCase(sortDir)
                ? Sort.by(sortField).descending()
                : Sort.by(sortField).ascending();

        List<Product> items =
                dao.findByPriceBetween(minPrice, maxPrice, sortSpec);

        model.addAttribute("items", items);
        model.addAttribute("min", min.orElse(null));
        model.addAttribute("max", max.orElse(null));
        model.addAttribute("sort", sortField);
        model.addAttribute("dir", sortDir);
        model.addAttribute("activeMenu", "search");

        return "product/search";
    }

    // ===== BÀI 2: SEARCH + PAGE =====
    @RequestMapping("/product/search-and-page")
    public String searchAndPage(Model model,
                                @RequestParam("keywords") Optional<String> kw,
                                @RequestParam("p") Optional<Integer> p) {

        String keywords = kw.orElse(session.get("keywords", ""));
        session.set("keywords", keywords);

        Pageable pageable = PageRequest.of(p.orElse(0), 5);
        Page<Product> page = dao.findByKeywords("%" + keywords + "%", pageable);

        model.addAttribute("page", page);
        model.addAttribute("activeMenu", "search-and-page");
        return "product/search-and-page";
    }




}
