package com.example.pl_lab_server.Common.util;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class HtmlTemplateUtil {

    public String createHtmlEmailTemplate(String authCode, String username, String email) {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>관리자 회원가입 인증</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: 'Arial', sans-serif;\n" +
                "            line-height: 1.6;\n" +
                "            color: #333;\n" +
                "            max-width: 600px;\n" +
                "            margin: 0 auto;\n" +
                "            padding: 20px;\n" +
                "        }\n" +
                "        .container {\n" +
                "            border: 1px solid #ddd;\n" +
                "            border-radius: 5px;\n" +
                "            padding: 20px;\n" +
                "            background-color: #f9f9f9;\n" +
                "        }\n" +
                "        .header {\n" +
                "            text-align: center;\n" +
                "            padding-bottom: 10px;\n" +
                "            border-bottom: 2px solid #3498db;\n" +
                "            margin-bottom: 20px;\n" +
                "        }\n" +
                "        .auth-code {\n" +
                "            font-size: 24px;\n" +
                "            font-weight: bold;\n" +
                "            text-align: center;\n" +
                "            color: #3498db;\n" +
                "            padding: 10px;\n" +
                "            margin: 15px 0;\n" +
                "            background-color: #eaf2f8;\n" +
                "            border-radius: 5px;\n" +
                "            letter-spacing: 2px;\n" +
                "        }\n" +
                "        .info-label {\n" +
                "            font-weight: bold;\n" +
                "            color: #555;\n" +
                "        }\n" +
                "        .footer {\n" +
                "            text-align: center;\n" +
                "            margin-top: 30px;\n" +
                "            font-size: 12px;\n" +
                "            color: #777;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <div class=\"header\">\n" +
                "            <h2>관리자 회원가입 인증 요청</h2>\n" +
                "        </div>\n" +
                "        <p>안녕하세요,</p>\n" +
                "        <p>관리자 회원가입 인증 요청 입니다. 아래 정보를 확인해 주세요.</p>\n" +
                "        \n" +
                "        <h3>인증 코드</h3>\n" +
                "        <div class=\"auth-code\">" + authCode + "</div>\n" +
                "        \n" +
                "        <p><span class=\"info-label\">요청자 이름:</span> " + username + "</p>\n" +
                "        <p><span class=\"info-label\">요청자 이메일:</span> " + email + "</p>\n" +
                "        \n" +
                "        <p>위 인증 코드를 입력하여 회원가입 절차를 완료해 주세요.</p>\n" +
                "        \n" +
                "        <div class=\"footer\">\n" +
                "            <p>본 메일은 발신 전용이므로 회신하실 수 없습니다.</p>\n" +
                "            <p>&copy; Programming Language Lab.</p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";
    }



}
