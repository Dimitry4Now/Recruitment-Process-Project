package finki.ukim.mk.projectv2.service;

import javax.mail.MessagingException;
import java.io.IOException;

public interface EmailService {
    public void sendSimpleMessage(String to, String subject, String message);
    public void sendMessageWithAttachment(String to, String subject, String body, String fileToAttach) throws MessagingException;
    public void check();
    public void fetch();
    void sendTask(String to, String subject, String body, byte[] data) throws MessagingException, IOException;
}
