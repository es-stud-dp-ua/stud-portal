package ua.dp.stud.createAccount.util;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Author: Pikus V.V.
 * Date: 25.03.13
 */
@RunWith(MockitoJUnitRunner.class)
public class MailerTest {
    private Mailer mailer;
    private JavaMailSender mockSender;
    private MimeMessage mockMessage;

    @Before
    public void setUp() {
        this.mailer = new Mailer();
        this.mockSender = mock(JavaMailSender.class);
        this.mailer.setMailSender(this.mockSender);
        mockMessage = mock(MimeMessage.class);
    }

    @Test
    public void sendMailTest() {
        String from = "admin@gmail.com";
        String to = "user@gmail.com";
        String subject = "New subject";
        String msg = "Message text";
        when(mockSender.createMimeMessage()).thenReturn(mockMessage);
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mockMessage, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(msg);
        } catch (MessagingException e) {

        }
        try {
            this.mailer.sendMail(from, to, subject, msg);
            verify(mockSender).send(mockMessage);
        } catch (MessagingException e) {
            assertTrue(false);
        }
    }
    //todo:wtf?
    /*@Test
    public void sendActivationMailTest()
    {
        String sendTo = "user@gmail.com";
        String hash = "http://stud.dp.ua/registration?hash=a1371b387cd19de3d78c367a2371d25379083";
        String subject = "Регистрация";
        String sendFrom = "studdpuasender@gmail.com";
        StringBuilder text = new StringBuilder();
        text.append("<p>Здравствуйте!<br>Вы успешно зарегистрировались на студентческом портале «Stud_portal.dp.ua».</p>")
                .append("<p><b>Для подтверждения регистрации перейдите по ссылке:</b></p>")
                .append("<p><a href=\"").append(hash)
                .append("\" target=\"_blank\">Activation me").append("</a></p>")
                .append("<hr><p style=\"color:#666;font-size:12px\">Данное письмо создано автоматически, пожалуйста, не отвечайте на него.<br>С уважением, администрация «Stud_portal.dp.ua»</p>");
        when(mockSender.createMimeMessage()).thenReturn(mockMessage);
        try
        {
            MimeMessageHelper helper = new MimeMessageHelper(mockMessage, true);
            helper.setFrom(sendFrom);
            helper.setTo(sendTo);
            helper.setSubject(subject);
            helper.setText(text.toString(), true);
        }
        catch (MessagingException e)
        {

        }
        this.mailer.sendActivationMail(sendTo, hash);
        verify(mockSender).send(mockMessage);
    }*/
}
