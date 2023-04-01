package edu.kit.scholarizer.security.mail;

/**
 * This interface defines the methods for the MailSenderService.
 * It is used to send confirmation E-Mails to users.
 * @author Tim Wolk
 * @version 1.0
 */
public interface IMailSenderService {
    /**
     * This method sends an E-Mail.
     * @param to The recipient of the E-Mail.
     * @param subject The subject of the E-Mail.
     * @param text The text of the E-Mail.
     */
    void sendMail(String to, String subject, String text);
}
