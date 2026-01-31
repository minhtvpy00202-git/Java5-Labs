package com.poly.Lab5.config;

import jakarta.servlet.http.Cookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        var user = User.builder()
                .username("poly")
                .password(encoder.encode("123"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, UserDetailsService uds) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/account/login", "/item/index", "/", "/error").permitAll()
                        .requestMatchers("/cart/**").authenticated()
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/account/login")
                        .loginProcessingUrl("/account/login")
                        .successHandler((request, response, authentication) -> {
                            String remember = request.getParameter("remember");
                            if (remember != null) {
                                Cookie cookie = new Cookie("user", authentication.getName());
                                cookie.setMaxAge(24 * 60 * 60 * 10);
                                cookie.setPath("/");
                                cookie.setHttpOnly(true);
                                cookie.setSecure(request.isSecure());
                                response.addCookie(cookie);
                            }
                            response.sendRedirect("/item/index");
                        })
                        .failureUrl("/account/login?error")
                        .permitAll()
                )
                .rememberMe(rm -> rm
                        .key("lab5-remember-key")
                        .userDetailsService(uds)
                        .tokenValiditySeconds(24 * 60 * 60 * 10)
                        .rememberMeParameter("remember")
                        .useSecureCookie(true)
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/account/login?logout")
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .csrf(Customizer.withDefaults());
        return http.build();
    }
}
