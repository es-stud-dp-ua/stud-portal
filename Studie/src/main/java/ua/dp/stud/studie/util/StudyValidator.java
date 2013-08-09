package ua.dp.stud.studie.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.dp.stud.studie.model.Studie;

import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Vladislav Pikus
 */
@Component("studyValidator")
public class StudyValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Studie.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Studie studie = (Studie)o;
        //crazy site pattern ;-)
        Pattern webSitePattern = Pattern.compile("\\b((?:[a-z][\\w-]+:(?:\\/{1,3}|[a-z0-9%])|www\\d{0,3}[.]|[a-z0-9.\\-]+[.][a-z]{2,4}\\/)(?:[^\\s()<>]+|\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\))+(?:\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\)|[^\\s`!()\\[\\]{};:'\".,<>?\\u00ab\\u00bb\\u201c\\u201d\\u2018\\u2019]))");
        Matcher webSiteMatch = webSitePattern.matcher(studie.getWebsite());
        if(!webSiteMatch.matches()) {
            errors.rejectValue("website", "validation.website.NotPattern");
        }
        Pattern phonePattern = Pattern.compile("((\\+3)?8?[\\- ]?)(\\(?(\\d{3,4})\\)?[\\- ]?)?[\\d\\- ]{7,10}");
        Matcher phoneMatch = phonePattern.matcher(studie.getPhone());
        if (!phoneMatch.matches()) {
            errors.rejectValue("phone", "validation.phone.NotPattern");
        }
        String[] phones = studie.getPhoneAdmissions().split(", ");
        Set<String> phoneSet = new TreeSet<String>();
        for(String phone : phones) {
            phoneSet.add(phone);
            phoneMatch = phonePattern.matcher(phone);
            if(!phoneMatch.matches()) {
                errors.rejectValue("phoneAdmissions", "validation.phone.NotPattern");
            }
        }
        if (phoneSet.size() != phones.length) {
            errors.rejectValue("phoneAdmissions", "validation.phone.Duplicate");
        }
    }
}
