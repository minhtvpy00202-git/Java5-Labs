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


    @GetMapping("/input")
    public String nhap() {
        return "hinhchunhat";
    }

    @PostMapping("/result")
    public String ketQua(Model model) {
        DecimalFormat df = new DecimalFormat("#.##");

        double chieuDai = Double.parseDouble(request.getParameter("chieuDai"));
        double chieuRong = Double.parseDouble(request.getParameter("chieuRong"));
        double chieuCao = Double.parseDouble(request.getParameter("chieuCao"));

        double chuVi = 2 * (chieuDai + chieuRong);
        double dienTich = chieuDai * chieuRong;
        double theTich = chieuDai * chieuRong * chieuCao;



        model.addAttribute("chuVi", df.format(chuVi));
        model.addAttribute("dienTich", df.format(dienTich));
        model.addAttribute("theTich", df.format(theTich));

        return "hinhchunhat";
    }
}
