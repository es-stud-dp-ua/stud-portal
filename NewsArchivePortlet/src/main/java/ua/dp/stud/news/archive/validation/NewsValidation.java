package ua.dp.stud.news.archive.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.dp.stud.StudPortalLib.model.News;

/**
 * @author Vladislav Pikus
 */
@Component
public class NewsValidation implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return News.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        News news = (News) o;
        if (news.getTopic() == null || news.getTopic().replaceAll(" ", "").equals("")) {
            errors.rejectValue("topic", "news.topic.empty");
        } else {
            int topicLength = news.getTopic().length();
            if (topicLength < 5 || topicLength > 100) {
                errors.rejectValue("topic", "news.topic.between");
            }
        }
        if (news.getText() == null || news.getText().replaceAll(" ", "").equals("")) {
            errors.rejectValue("text", "news.text.empty");
        } else {
            int textLength = news.getText().length();
            if (news.getText() != null && (textLength < 100 || textLength > 10000)) {
                errors.rejectValue("text", "news.text.between");
            }
        }
        if (news.getInCalendar() && news.getPublicationInCalendar() == null) {
            errors.rejectValue("publicationInCalendar", "news.publicationInCalendar.empty");
        }
    }
}
