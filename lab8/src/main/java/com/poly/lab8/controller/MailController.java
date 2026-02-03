package com.poly.lab8.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.poly.lab8.service.MailService;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/mail")
public class MailController {

    @Autowired
    MailService mailService;

    @GetMapping("/send")
    public String form() {
        return "mail/send";
    }

    @PostMapping("/send")
    public String send(
            @RequestParam String to,
            @RequestParam(required = false) String cc,
            @RequestParam(required = false) String bcc,
            @RequestParam String subject,
            @RequestParam String body,
            @RequestParam(name = "files", required = false) MultipartFile[] files) {

        String uploadDir = System.getProperty("java.io.tmpdir") + "/lab8-mail";
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        StringBuilder filenames = new StringBuilder();
        if (files != null) {
            for (MultipartFile file : files) {
                if (file != null && !file.isEmpty()) {
                    try {
                        String safeName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                        File dest = new File(dir, safeName);
                        file.transferTo(dest);
                        filenames.append(dest.getAbsolutePath()).append(",");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        MailService.Mail mail = MailService.Mail.builder()
                .to(to)
                .cc(cc)
                .bcc(bcc)
                .subject(subject)
                .body(body)
                .filenames(filenames.toString())
                .build();

        mailService.push(mail);
        return "redirect:/";
    }

    @GetMapping("/status")
    public String status(Model model) {
        model.addAttribute("queueSize", mailService.getQueueSize());
        model.addAttribute("sentCount", mailService.getSentCount());
        return "mail/status";
    }

    @GetMapping("/status/data")
    @ResponseBody
    public Map<String, Integer> statusData() {
        Map<String, Integer> map = new HashMap<>();
        map.put("queueSize", mailService.getQueueSize());
        map.put("sentCount", mailService.getSentCount());
        return map;
    }


}
