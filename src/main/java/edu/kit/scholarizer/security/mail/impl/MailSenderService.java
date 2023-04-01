package edu.kit.scholarizer.security.mail.impl;

import edu.kit.scholarizer.security.ValidEmail;
import edu.kit.scholarizer.security.mail.IMailSenderService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * This class implements the methods of the IMailSenderService interface.
 * @author Tim Wolk
 * @version 1.0
 */
@Service
public class MailSenderService implements IMailSenderService {
    private static final String SENDER_ADDRESS = "scholarizer@gmail.com";

    private final JavaMailSender mailSender;

    /**
     * Constructor for the mail sender service (Dependency injection).
     * @param mailSender The mail sender to use
     */
    public MailSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendMail(@ValidEmail String to, String subject, String text) {
        if (subject == null || text == null) {
            throw new IllegalArgumentException("Invalid arguments");
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(SENDER_ADDRESS);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        this.mailSender.send(message);
    }
}
