package com.poly.Lab5.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class ParamService {

    @Autowired
    HttpServletRequest request;

    public String getString(String name, String defaultValue) {
        String value = request.getParameter(name);
        return value != null ? value : defaultValue;
    }

    public int getInt(String name, int defaultValue) {
        try {
            return Integer.parseInt(getString(name, null));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public double getDouble(String name, double defaultValue) {
        try {
            return Double.parseDouble(getString(name, null));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public boolean getBoolean(String name, boolean defaultValue) {
        try {
            return Boolean.parseBoolean(getString(name, null));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public String save(String name, String folder) {
        MultipartFile file = null;
        if (request instanceof MultipartHttpServletRequest multipartRequest) {
            file = multipartRequest.getFile(name);
        }
        if (file == null || file.isEmpty()) {
            try {
                Part part = request.getPart(name);
                if (part == null || part.getSize() == 0) {
                    return "";
                }
                String original = StringUtils.cleanPath(part.getSubmittedFileName() == null ? "" : part.getSubmittedFileName());
                String filename = UUID.randomUUID() + "_" + original;
                Path dir = Paths.get(folder);
                if (!dir.isAbsolute()) {
                    dir = Paths.get(System.getProperty("user.dir"), folder);
                }
                Files.createDirectories(dir);
                Path target = dir.resolve(filename);
                part.write(target.toString());
                return filename;
            } catch (Exception e) {
                return "";
            }
        }
        if (file == null || file.isEmpty()) {
            return "";
        }
        String original = StringUtils.cleanPath(file.getOriginalFilename() == null ? "" : file.getOriginalFilename());
        String filename = UUID.randomUUID() + "_" + original;
        Path dir = Paths.get(folder);
        if (!dir.isAbsolute()) {
            dir = Paths.get(System.getProperty("user.dir"), folder);
        }
        try {
            Files.createDirectories(dir);
            Files.copy(file.getInputStream(), dir.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
            return filename;
        } catch (IOException e) {
            return "";
        }
    }
}
