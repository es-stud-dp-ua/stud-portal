package ua.dp.stud.studie.validation.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

/**
 * @author Vladislav Pikus
 */
@Documented
@Constraint(validatedBy = {PhoneListConstraintValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD,ElementType.ANNOTATION_TYPE,ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
@NotNull
public @interface PhoneList {
    boolean duplicate() default true;

    String message() default "{PhoneList}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
