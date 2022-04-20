package finki.ukim.mk.projectv2.service.impl;

import finki.ukim.mk.projectv2.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class EmailServiceImpl implements EmailService {

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

    @Override
    public void sendMessageWithAttachment(String to, String subject, String body, String fileToAttach) throws MessagingException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom("recruitment.process.project@gmail.com");
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(body);

        FileSystemResource fileSystemResource = new FileSystemResource(new File(fileToAttach));

        mimeMessageHelper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);

        emailSender.send(mimeMessage);

        System.out.println("Mail with attachment sent successfully");
    }

}

