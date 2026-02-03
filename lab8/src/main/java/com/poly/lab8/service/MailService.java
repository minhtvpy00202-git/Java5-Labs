package com.poly.lab8.service;

import lombok.Builder;
import lombok.Data;
import lombok.Builder.Default;

public interface MailService {

    @Data
    @Builder
    public static class Mail {
        @Default
        private String from = "WebShop <webshop@gmail.com>";
        private String to, cc, bcc, subject, body, filenames;
    }

    void send(Mail mail);
    void push(Mail mail);

    default void send(String to, String subject, String body) {
        send(Mail.builder().to(to).subject(subject).body(body).build());
    }

    default void push(String to, String subject, String body) {
        push(Mail.builder().to(to).subject(subject).body(body).build());
    }
    int getQueueSize();
    int getSentCount();
}
