package org.example.springarchitecture.user.service;

import lombok.RequiredArgsConstructor;
import org.example.springarchitecture.user.service.port.MailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CertificationService {

    private final MailSender mailSender;

    public void send(String email, Long userId, String certificationUrl) {
        String url = generateCertificationUrl(userId, certificationUrl);
        System.out.println(url + "================= 메일보냈다 =============");
        String title = "메일제목";
        String content = "본문 " + url;
        mailSender.send(email, title, content);
    }

    private String generateCertificationUrl(long userId, String certificationCode) {
        return "URL ==> " + userId + " == " + certificationCode;
    }

}
