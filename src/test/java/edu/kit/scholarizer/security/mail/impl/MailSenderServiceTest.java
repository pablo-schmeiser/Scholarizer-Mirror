package edu.kit.scholarizer.security.mail.impl;

import edu.kit.scholarizer.security.mail.IMailSenderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.validation.constraints.Null;

import static org.junit.jupiter.api.Assertions.*;

class MailSenderServiceTest {
    private static final String TEST_ADDRESS = "123@yahoo.com";
    private static final String TEST_SUBJECT = "subject";
    private static final String TEST_TEXT = "text";

    @Test
    void constructorTest() {
        assertDoesNotThrow(() -> new MailSenderService(new JavaMailSenderImpl()));
        MailSenderService mss = new MailSenderService(new JavaMailSenderImpl());
        assertNotNull(mss);
        assertInstanceOf(IMailSenderService.class, mss);
        assertInstanceOf(MailSenderService.class, mss);
    }

    @Test
    void sendMailTest() {
        MailSenderService mss = new MailSenderService(new JavaMailSenderImpl());

        // Doesn't avoid sending mails!
        assertDoesNotThrow(() -> mss.sendMail(TEST_ADDRESS, "subject", "text"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"test", "test@test", "test@test.", "@test.com", "test@.com", "test.com", "test@", "test@.",
        "@test", "@test.", ".com", "@test.com", "@test@.com", "test@test..com", "test@test@.com", "test@@test.com"})
    @EmptySource
    @NullSource
    void sendMailToInvalidAddress(String to) {
        MailSenderService mss = new MailSenderService(new JavaMailSenderImpl());
        assertThrows(Exception.class, () -> mss.sendMail(to, TEST_SUBJECT, TEST_TEXT));
    }

    @Test
    void sendMailWithNullSubject() {
        MailSenderService mss = new MailSenderService(new JavaMailSenderImpl());
        assertThrows(IllegalArgumentException.class, () -> mss.sendMail(TEST_ADDRESS, null, TEST_TEXT));
    }

    @Test
    void sendMailWithNullText() {
        MailSenderService mss = new MailSenderService(new JavaMailSenderImpl());
        assertThrows(IllegalArgumentException.class, () -> mss.sendMail(TEST_ADDRESS, TEST_SUBJECT, null));
    }

}
