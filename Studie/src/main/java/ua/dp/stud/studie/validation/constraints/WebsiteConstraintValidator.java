package ua.dp.stud.studie.validation.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Vladislav Pikus
 */
public class WebsiteConstraintValidator implements ConstraintValidator<Website, String> {

    @Override
    public void initialize(Website website) { }

    @Override
    public boolean isValid(String t, ConstraintValidatorContext cvc) {
        Pattern webSitePattern = Pattern.compile("\\b((?:[a-z][\\w-]+:(?:\\/{1,3}|[a-z0-9%])|www\\d{0,3}[.]|[a-z0-9.\\-]+[.][a-z]{2,4}\\/)(?:[^\\s()<>]+|\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\))+(?:\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\)|[^\\s`!()\\[\\]{};:'\".,<>?\\u00ab\\u00bb\\u201c\\u201d\\u2018\\u2019]))");
        Matcher webSiteMatch = webSitePattern.matcher(t);
        return webSiteMatch.matches();
    }
}
