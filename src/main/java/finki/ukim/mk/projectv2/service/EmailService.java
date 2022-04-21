package finki.ukim.mk.projectv2.service;

import javax.mail.MessagingException;

public interface EmailService {
    public void sendSimpleMessage(String to, String subject, String message);
    public void sendMessageWithAttachment(String to, String subject, String body, String fileToAttach) throws MessagingException;
    public void check(String host, String storeType, String user, String password);
    public void fetch(String host, String storeType, String user, String password);
}
