package com.poly.lab8.interceptor;

import jakarta.servlet.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import com.poly.lab8.entity.Account;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest req,
                             HttpServletResponse resp,
                             Object handler) throws Exception {

        HttpSession session = req.getSession();
        session.setAttribute("securityUri", req.getRequestURI());

        Account user = (Account) session.getAttribute("user");

        if (user == null) {
            resp.sendRedirect("/auth/login");
            return false;
        }

        if (req.getRequestURI().startsWith("/admin") && !user.getAdmin()) {
            resp.sendRedirect("/auth/login");
            return false;
        }

        return true;
    }
}
