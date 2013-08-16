package ua.dp.stud.studie.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.dp.stud.studie.model.Studie;

/**
 * @author Vladislav Pikus
 */
@Component("studyValidator")
public class StudyValidator implements Validator {

    private static final String VALIDATION_NONE = "validation.None";

    @Override
    public boolean supports(Class<?> aClass) {
        return Studie.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Studie studie = (Studie)o;
        if(studie.getStatus().equals("NONE")) {
            errors.rejectValue("status", VALIDATION_NONE);
        }
        if(studie.getAccreditacion().equals("NONE")) {
            errors.rejectValue("accreditacion", VALIDATION_NONE);
        }
        if(studie.getOnGraduation().equals("NONE")) {
            errors.rejectValue("onGraduation", VALIDATION_NONE);
        }
    }
}
