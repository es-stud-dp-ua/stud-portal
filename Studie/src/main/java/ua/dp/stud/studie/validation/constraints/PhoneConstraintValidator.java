package ua.dp.stud.studie.validation.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Vladislav Pikus
 */
public class PhoneConstraintValidator implements ConstraintValidator<Phone, String> {

    @Override
    public void initialize(Phone phone) { }

    @Override
    public boolean isValid(String t, ConstraintValidatorContext cvc) {
        Pattern phonePattern = Pattern.compile("((\\+3)?8?[\\- ]?)(\\(?(\\d{3,4})\\)?[\\- ]?)?[\\d\\- ]{7,10}");
        Matcher phoneMatch = phonePattern.matcher(t);
        return phoneMatch.matches();
    }
}
