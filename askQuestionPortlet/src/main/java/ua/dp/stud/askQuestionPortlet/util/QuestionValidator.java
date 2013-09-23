package ua.dp.stud.askQuestionPortlet.util;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ua.dp.stud.askQuestionPortlet.model.Question;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Question validator
 *
 * @author Oleg
 */
public class QuestionValidator implements Validator {
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /**
     * required method
     *
     * @param klass
     * @return
     */
    public boolean supports(Class<?> klass) {
        return Question.class.isAssignableFrom(klass);
    }

    /**
     * Validation method
     *
     * @param target
     * @param errors
     */
    public void validate(Object target, Errors errors) {
        Question question = (Question) target;
        String name = question.getSentFrom();

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(name);
        if (!matcher.matches()) {
            // wrong email
            errors.rejectValue("sentFrom", "NotEmpty.question.sentFrom");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "text", "NotEmpty.question.text");
    }
}
