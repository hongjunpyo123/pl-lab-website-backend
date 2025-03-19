package com.example.pl_lab_server.Common.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Getter
@Component
public class MailUtil {

    @Autowired
    private JavaMailSender emailSender;

    private String[] recipients = {};

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("pllab1004@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    public void sendHtmlEmail(String[] to, String subject, String htmlContent) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom("PL Lab <pllab1004@gmail.com>");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);

        emailSender.send(message);
    }

    public void addRecipient(String email) {
        // 1. 현재 배열보다 크기가 1 큰 새 배열 생성
        String[] newRecipients = new String[recipients.length + 1];

        // 2. 기존 배열의 모든 요소를 새 배열로 복사
        System.arraycopy(recipients, 0, newRecipients, 0, recipients.length);

        // 3. 새 이메일 주소를 배열의 마지막 위치에 추가
        newRecipients[recipients.length] = email;

        // 4. recipients 참조를 새 배열로 업데이트
        recipients = newRecipients;
    }

    public void clearRecipients() {
        recipients = new String[0];
    }


}

