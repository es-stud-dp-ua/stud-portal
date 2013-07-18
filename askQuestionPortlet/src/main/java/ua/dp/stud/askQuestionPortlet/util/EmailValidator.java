package ua.dp.stud.askQuestionPortlet.util;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.dp.stud.askQuestionPortlet.model.MailChanger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Email validator
 *
 * @author Alex
 */
public class EmailValidator implements Validator {
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /**
     * required method
     *
     * @param klass
     * @return
     */
    public boolean supports(Class<?> klass) {
        return MailChanger.class.isAssignableFrom(klass);
    }

    /**
     * Validation method
     *
     * @param target
     * @param errors
     */
    public void validate(Object target, Errors errors) {
        MailChanger mailChanger = (MailChanger) target;
        String name = mailChanger.getNewMail();

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(name);
        if (!matcher.matches()) {
            errors.rejectValue("newMail", "NotEmpty.newMail");
        }
    }
}