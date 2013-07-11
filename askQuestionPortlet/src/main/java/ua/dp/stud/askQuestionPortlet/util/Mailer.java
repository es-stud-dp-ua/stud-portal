package ua.dp.stud.askQuestionPortlet.util;

import com.liferay.util.portlet.PortletProps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class for sending emails.
 * Depends on javax.mail and spring framework
 */

@Component
public class Mailer {

    @Autowired
    private MailSender mailSender;


    private static Logger log = Logger.getLogger(Mailer.class.getName());

    /**
     * Sets mail sender
     *
     * @param mailSender
     */
    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * object sends out an email through this method
     *
     * @param sentFrom Email adress of a question sender
     * @param subject  Subject of email
     * @param text     Email body (text)
     */
    public void sendMail(String sentFrom, String subject, String text) {
        String newLine = "\n";

        SimpleMailMessage message = new SimpleMailMessage();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("from: ").append(sentFrom).append(newLine).append(newLine);

        //subject could be null, that's why this check is needed
        if (subject != null && !subject.trim().equals("")) {
            stringBuilder.append("subject: ").append(subject).append(newLine).append(newLine);
        }
        stringBuilder.append(text);

        message.setFrom(sentFrom);
        message.setTo(PortletProps.get("admin_mail"));
        message.setSubject(subject);
        message.setText(stringBuilder.toString());

        mailSender.send(message);
    }

    /**
     * Sets new admin's email and saves it in the property file
     *
     * @param newMail
     */
    public void setNewMail(String newMail) {
        PortletProps.set("admin_mail", newMail);
        Properties prop = PortletProps.getProperties();
        prop.setProperty("admin_mail", newMail);
        try {
            prop.store(new FileOutputStream(PortletProps.get("base.path")), "new mail setted");
        } catch (IOException e) {
            log.log(Level.SEVERE, "Exception: ", e);
        }
    }
}
