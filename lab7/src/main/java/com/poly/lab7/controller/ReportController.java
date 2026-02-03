package com.poly.lab7.controller;

import com.poly.lab7.dao.ProductDAO;
import com.poly.lab7.report.Report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

@Controller
public class ReportController {

    @Autowired
    ProductDAO dao;

    @RequestMapping("/report/inventory-by-category")
    public String inventory(Model model) {

        List<Report> items = dao.getInventoryByCategory();
        model.addAttribute("items", items);
        model.addAttribute("activeMenu", "report");

        Long totalCount = items.stream()
                .mapToLong(Report::getCount)
                .sum();
        Double totalSum = items.stream()
                .mapToDouble(r -> r.getSum() != null ? r.getSum() : 0.0)
                .sum();

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');

        DecimalFormat intFmt = new DecimalFormat("#,###", symbols);
        intFmt.setMaximumFractionDigits(0);

        String formattedTotalCount = intFmt.format(totalCount);
        String formattedTotalSum = intFmt.format(totalSum);

        model.addAttribute("totalCount", formattedTotalCount);
        model.addAttribute("totalSum", formattedTotalSum);

        return "report/inventory-by-category";
    }
}
