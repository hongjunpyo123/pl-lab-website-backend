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


    public String createApplicationCompletionEmailTemplate(String username) {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>PL Lab 지원 완료 안내</title>\n" +
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
                "            border-bottom: 2px solid #6366f1;\n" +
                "            margin-bottom: 20px;\n" +
                "        }\n" +
                "        .success-icon {\n" +
                "            font-size: 48px;\n" +
                "            text-align: center;\n" +
                "            color: #10b981;\n" +
                "            margin: 20px 0;\n" +
                "        }\n" +
                "        .message {\n" +
                "            font-size: 18px;\n" +
                "            text-align: center;\n" +
                "            padding: 15px;\n" +
                "            margin: 15px 0;\n" +
                "            background-color: #f0f9ff;\n" +
                "            border-radius: 5px;\n" +
                "        }\n" +
                "        .next-steps {\n" +
                "            background-color: #f5f3ff;\n" +
                "            border-left: 4px solid #6366f1;\n" +
                "            padding: 15px;\n" +
                "            margin: 20px 0;\n" +
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
                "            <h2>PL Lab 지원 완료 안내</h2>\n" +
                "        </div>\n" +
                "        \n" +
                "        <div class=\"success-icon\">✓</div>\n" +
                "        \n" +
                "        <p>안녕하세요, <strong>" + username + "</strong>님</p>\n" +
                "        \n" +
                "        <div class=\"message\">\n" +
                "            PL Lab 지원이 성공적으로 완료되었습니다.\n" +
                "        </div>\n" +
                "        <div class=\"next-steps\">\n" +
                "            <h3>다음 단계</h3>\n" +
                "            <p>1. 서류 심사 결과는 개별적으로 이메일을 통해 안내될 예정입니다.</p>\n" +
                "            <p>2. 서류 합격자에 한해 면접 일정이 공지됩니다.</p>\n" +
                "            <p>3. 최종 합격 여부는 면접 이후 개별 연락 드립니다.</p>\n" +
                "        </div>\n" +
                "        \n" +
                "        <p>지원 과정에서 문의사항이 있으시면 이 이메일로 바로 회신해 주시기 바랍니다.</p>\n" +
                "        \n" +
                "        <div class=\"footer\">\n" +
                "            <p>&copy; Programming Language Lab.</p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";
    }





}
