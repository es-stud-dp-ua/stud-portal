package ua.dp.stud.createAccount.util;


import com.liferay.portal.kernel.util.Validator;
import org.springframework.validation.Errors;
import ua.dp.stud.createAccount.model.UserInfo;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: Tradunsky V.V.
 * Date: 22.02.13
 * Time: 0:33
 */
public class UserInfoValidator extends Validator {

    private static String strNOTEMPTYNECESSARYFIELDS = "NotEmpty.user.necessaryFields";
    private static String strNOTEMPTYPASSWORD = "NotEmpty.user.password";

    /**
     * required method
     * @param someClass some class for check supports
     * @return decision of a supports someClass
     */
    public boolean supports(Class<?> someClass) {
        return UserInfo.class.isAssignableFrom(someClass);
    }

    /**
     * Necessary fields validation
     * @param target is a new user info
     * @param errors errors registration
     */
    public void necessaryFields(Object target, Errors errors) {
        UserInfo userInfo = (UserInfo) target;
        if (!isEmailAddress(userInfo.getEmailAddress())){
            //wrong mail
            errors.rejectValue("emailAddress", "NotEmpty.user.Mail");
        }
        Pattern pattern = Pattern.compile("^[а-яА-ЯёЁa-zA-Z.-]+$");
        Matcher matcherFirstName = pattern.matcher(userInfo.getFirstName());
        Matcher matcherLastName = pattern.matcher(userInfo.getLastName());
        if ((!matcherLastName.matches()) || (!matcherFirstName.matches()))
        {
            errors.rejectValue("firstName", strNOTEMPTYNECESSARYFIELDS);
        }

        if((!isPassword(userInfo.getPassword1())) || (!isPassword(userInfo.getPassword2())))
		{
			errors.rejectValue("password1", strNOTEMPTYPASSWORD);
            errors.rejectValue("password2", strNOTEMPTYPASSWORD);
		}
		else
		{
			if (!userInfo.getPassword1().equals(userInfo.getPassword2()))
			{
				errors.rejectValue("password2", "NotEmpty.user.passwordNotEquals");
			}
		}
    }
}
