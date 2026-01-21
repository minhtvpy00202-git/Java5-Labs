package com.poly.Lab4.controller;

import com.poly.Lab4.bean.Staff;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.Binding;

@Controller
public class StaffController {
    @RequestMapping("/staff/create/form")
    public String createForm(Model model, @ModelAttribute("staff") Staff staff) {
        model.addAttribute("message", "Vui lòng nhập thông tin nhân viên!");
        return "staff-create";
    }

    @RequestMapping("/staff/create/save")
    public String createSave(Model model, @Valid @ModelAttribute("staff") Staff staff,
                             BindingResult rs,
                             @RequestPart("photo_file")MultipartFile photoFile) {

        if(rs.hasErrors()) {
            model.addAttribute("message", "Vui lòng sửa các lỗi bên dưới!");
            return "staff-create";
        } else {
            model.addAttribute("message", "Dữ liệu đã nhập đúng!");
        }

        if(!photoFile.isEmpty()){
            staff.setPhoto(photoFile.getOriginalFilename());
        }

        model.addAttribute("message", "Xin chào " + staff.getFullname());

        return "staff-create";

    }
}
