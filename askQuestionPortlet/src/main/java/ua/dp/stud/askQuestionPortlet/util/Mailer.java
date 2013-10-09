package ua.dp.stud.askQuestionPortlet.util;

import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.util.mail.MailEngine;
import com.liferay.util.mail.MailEngineException;
import com.liferay.util.portlet.PortletProps;
import org.apache.commons.lang.exception.NestableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.antlr.stringtemplate.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Class for sending emails.
 * Depends on javax.mail and spring framework
 */

@Component
public class Mailer {

    private static Logger log = Logger.getLogger(Mailer.class.getName());

    /**
     * object sends out an email through this method
     *
     * @param sentFrom Email adress of a question sender
     * @param subject  Subject of email
     * @param text     Email body (text)
     * @param name     Name of a question sender
     * @param mobile   Mobile number of a question sender
     */
    public void sendMail(String sentFrom, String subject, String text, String name, String mobile) throws Exception{
        StringTemplate message1 = new StringTemplate("FROM: $from$ \nSUBJECT: $subj$ \n$text$ \n______\n$fromname$, $frommobile$");
        message1.setAttribute("from", sentFrom);
        message1.setAttribute("subj", subject);
        message1.setAttribute("text", text);
        message1.setAttribute("fromname", name);
        message1.setAttribute("frommobile", mobile);
        InternetAddress fr = new InternetAddress(sentFrom);
        Properties sessionProperties = MailEngine.getSession().getProperties();
        String adminMail = sessionProperties.getProperty("mail.smtp.user").equals("") ? sessionProperties.getProperty("mail.smtps.user") : sessionProperties.getProperty("mail.smtp.user");
        //String adminMail = "studdpuasender@gmail.com";
        InternetAddress to = new InternetAddress(adminMail);
        MailEngine.send(fr, to, subject, message1.toString());
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
