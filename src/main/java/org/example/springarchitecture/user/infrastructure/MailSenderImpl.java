package org.example.springarchitecture.user.infrastructure;

import org.example.springarchitecture.user.service.port.MailSender;
import org.springframework.stereotype.Component;

@Component
public class MailSenderImpl implements MailSender {

    @Override
    public void send(String email, String title, String content) {
        System.out.println(email + " " + title + " " + content);
    }
}
