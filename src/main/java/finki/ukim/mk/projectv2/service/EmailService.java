package finki.ukim.mk.projectv2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(String to, String subject, String message){
        SimpleMailMessage message1 = new SimpleMailMessage();

        message1.setFrom("recruitment.process.project@gmail.com");
        message1.setTo(to);
        message1.setSubject(subject);
        message1.setText(message);

        emailSender.send(message1);

        System.out.println("Mail sent successfully");

    }

}

