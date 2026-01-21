package poly.edu.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DecimalFormat;

@Controller
@RequestMapping("/hinhchunhat")
public class hinhChuNhatController {

    @Autowired
    HttpServletRequest request;


    @GetMapping("/nhapvao")
    public String nhap() {
        return "hinhchunhat";
    }

    @PostMapping("/ketqua")
    public String ketQua(Model model) {
        DecimalFormat df = new DecimalFormat("#.##");

        double chieuDai = Double.parseDouble(request.getParameter("chieuDai"));
        double chieuRong = Double.parseDouble(request.getParameter("chieuRong"));

        double chuVi = 2 * (chieuDai + chieuRong);
        double dienTich = chieuDai * chieuRong;



        model.addAttribute("chuVi", df.format(chuVi));
        model.addAttribute("dienTich", df.format(dienTich));

        return "hinhchunhat";
    }
}
