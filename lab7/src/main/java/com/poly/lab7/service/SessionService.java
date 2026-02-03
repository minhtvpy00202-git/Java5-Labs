package com.poly.lab7.service;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.HashMap;
import java.util.Map;

@Service
@SessionScope
public class SessionService {

    private Map<String, Object> session = new HashMap<>();

    public void set(String name, Object value) {
        session.put(name, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String name, T defaultValue) {
        return (T) session.getOrDefault(name, defaultValue);
    }
}
