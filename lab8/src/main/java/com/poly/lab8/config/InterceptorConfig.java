package com.poly.lab8.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import com.poly.lab8.interceptor.*;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    AuthInterceptor auth;

    @Autowired
    LogInterceptor log;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 🔒 Chặn truy cập – cần đăng nhập
        registry.addInterceptor(auth)
                .addPathPatterns(
                        "/admin/**",
                        "/order/**",
                        "/account/**"
                )
                .excludePathPatterns("/admin/home/index");

        // 📝 Ghi log – TẤT CẢ request
        registry.addInterceptor(log)
                .addPathPatterns("/**");
    }
}
