package com.poly.lab8.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import org.springframework.core.io.FileSystemResource;
import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

@Service("mailService")
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    // HÀNG ĐỢI MAIL (FIFO)
    private final Queue<Mail> queue = new LinkedList<>();

    // ĐẾM SỐ MAIL ĐÃ GỬI
    private int sentCount = 0;

    /**
     * GỬI MAIL THẬT
     * CHỈ ĐƯỢC GỌI TỪ @Scheduled
     */
    @Override
    public void send(Mail mail) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(mail.getFrom());
            helper.setTo(mail.getTo());

            if (mail.getCc() != null && !mail.getCc().trim().isEmpty()) {
                String[] ccList = mail.getCc().split("[,;\\s]+");
                helper.setCc(ccList);
            }
            if (mail.getBcc() != null && !mail.getBcc().trim().isEmpty()) {
                String[] bccList = mail.getBcc().split("[,;\\s]+");
                helper.setBcc(bccList);
            }
            helper.setSubject(mail.getSubject());
            helper.setText(mail.getBody(), true);

            if (mail.getFilenames() != null && !mail.getFilenames().trim().isEmpty()) {
                String[] paths = mail.getFilenames().split(",");
                for (String p : paths) {
                    String path = p.trim();
                    if (!path.isEmpty()) {
                        FileSystemResource resource = new FileSystemResource(new File(path));
                        if (resource.exists()) {
                            helper.addAttachment(resource.getFilename(), resource);
                        }
                    }
                }
            }

            mailSender.send(message);

            if (mail.getFilenames() != null && !mail.getFilenames().trim().isEmpty()) {
                String[] paths = mail.getFilenames().split(",");
                for (String p : paths) {
                    String path = p.trim();
                    if (!path.isEmpty()) {
                        File f = new File(path);
                        if (f.exists()) {
                            try { f.delete(); } catch (Exception ignored) {}
                        }
                    }
                }
            }

            sentCount++; // tăng biến đếm sau khi gửi thành công

            System.out.println("✅ Đã gửi mail tới: " + mail.getTo());

        } catch (Exception e) {
            System.err.println("❌ Lỗi gửi mail: " + e.getMessage());
        }
    }

    /**
     * ĐẨY MAIL VÀO HÀNG ĐỢI
     * TUYỆT ĐỐI KHÔNG GỬI Ở ĐÂY
     */
    @Override
    public void push(Mail mail) {
        queue.offer(mail);
        System.out.println("📥 Đã thêm mail vào queue. Tổng: " + queue.size());
    }

    /**
     * TASK TỰ ĐỘNG CHẠY MỖI 5 GIÂY
     * MỖI LẦN GỬI 1 MAIL
     */
    @Scheduled(fixedDelay = 5000)
    public void processQueue() {
        if (!queue.isEmpty()) {
            Mail mail = queue.poll(); // lấy và xóa mail đầu tiên
            send(mail);
        }
    }

    @Override
    public int getQueueSize() {
        return queue.size();
    }

    @Override
    public int getSentCount() {
        return sentCount;
    }
}
