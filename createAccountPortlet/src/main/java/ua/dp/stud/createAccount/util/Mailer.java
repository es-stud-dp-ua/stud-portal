package ua.dp.stud.createAccount.util;

import com.liferay.util.portlet.PortletProps;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailParseException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**  Class for sending emails.
 *   @author Vladislav Pikus
 */

@Component
public class Mailer
{
    private static final Logger log = Logger.getLogger(Mailer.class.getName());

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Autowired
    private JavaMailSender mailSender;
	
	private static final String SUBJECT = "Регистрация";
	
	/**
     * Sets mail sender
     * @param mailSender
     */
    public void setMailSender(JavaMailSender mailSender)
	{
        this.mailSender = mailSender;
    }

    public void sendMail(String from, String to, String subject, String msg) throws MessagingException{
		MimeMessage message = this.mailSender.createMimeMessage();
		try
		{
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(msg);
		}
		catch (MessagingException ex)
		{
                        log.log(Level.SEVERE, "Exception: ", ex);
			throw new MailParseException(ex);
	    }
        mailSender.send(message);
    }


    /**
     * Object sends out an email through this method
     * @param sentTo			Email adress of new user
     * @param hash             Hash code
     */
    public void sendActivationMail(String sentTo, String hash)
	{

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(sentTo);
        if (!matcher.matches()) {
            //mb this error log
            return;
        }

        MimeMessage message = this.mailSender.createMimeMessage();
		try
		{
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
            //todo: check locale and change language of mail to appropriate
			StringBuilder text = new StringBuilder();
			text
                .append("<p>Здравствуйте!<br>Вы успешно зарегистрировались на студентческом портале «Stud_portal.dp.ua».</p>")
				.append("<p><b>Для подтверждения регистрации перейдите по ссылке:</b></p>")
				.append("<p><a href=\"").append(hash)
				.append("\" target=\"_blank\">Activation me").append("</a></p>")
				.append("<hr><p style=\"color:#666;font-size:12px\">Данное письмо создано автоматически, пожалуйста, не отвечайте на него.<br>С уважением, администрация «Stud_portal.dp.ua»</p>");

			helper.setFrom(PortletProps.get("admin_mail"));
			helper.setTo(sentTo);
			helper.setSubject(this.SUBJECT);
			helper.setText(text.toString(), true);

        }
		catch (MessagingException ex)
		{
                    log.log(Level.SEVERE, "Exception: ", ex);
                    throw new MailParseException(ex);
	    }

        this.mailSender.send(message);
    }
}
