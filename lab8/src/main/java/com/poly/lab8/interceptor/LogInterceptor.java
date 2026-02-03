package com.poly.lab8.interceptor;

import jakarta.servlet.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import com.poly.lab8.entity.Account;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Date;

@Component
public class LogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest req,
                             HttpServletResponse resp,
                             Object handler) {

        try (PrintWriter out = new PrintWriter(
                new FileWriter("logs/access.log", true))) {

            Account user =
                    (Account) req.getSession().getAttribute("user");

            out.println(
                    req.getRequestURI() + " | " +
                            new Date() + " | " +
                            (user != null ? user.getFullname() : "Guest")
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }
}
