package ua.dp.stud.studie.validation.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Vladislav Pikus
 */
public class OnlyCharactersConstraintValidator implements ConstraintValidator<OnlyCharacters, String> {
    @Override
    public void initialize(OnlyCharacters onlyCharacters) { }

    @Override
    public boolean isValid(String t, ConstraintValidatorContext cvc) {
        return t.matches("[а-яА-ЯёЁa-zA-Z. -]*");
    }
}
