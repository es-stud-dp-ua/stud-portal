package ua.dp.stud.studie.validation.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Vladislav Pikus
 */
public class PhoneListConstraintValidator implements ConstraintValidator<PhoneList, String> {

    private PhoneList phoneList;

    @Override
    public void initialize(PhoneList phoneList) {
        this.phoneList = phoneList;
    }

    @Override
    public boolean isValid(String t, ConstraintValidatorContext cvc) {
        Pattern phonePattern = Pattern.compile("((\\+3)?8?[\\- ]?)(\\(?(\\d{3,4})\\)?[\\- ]?)?[\\d\\- ]{7,10}");
        String[] phones = t.split(", ");
        Set<String> phoneSet = new TreeSet<String>();
        for(String phone : phones) {
            phoneSet.add(phone);
            Matcher phoneMatch = phonePattern.matcher(phone);
            if (!phoneMatch.matches()) {
                return false;
            }
        }
        if (!phoneList.duplicate()) {
            if (phoneSet.size() != phones.length) {
                return false;
            }
        }
        return true;
    }
}
