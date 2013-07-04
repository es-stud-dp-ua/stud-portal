package ua.dp.stud.createAccount.util;

import org.junit.Before;

/**
 * Author: Pikus V.V.
 * Date: 25.03.13
 */
public class UserInfoValidatorTest
{
    private UserInfoValidator userInfoValidator;

    @Before
    public void setUp()
    {
        userInfoValidator = new UserInfoValidator();
    }
    //todo:wtf?
    /*@Test
    public void necessaryFieldsNotValidTest()
    {
        UserInfo validUser = new UserInfo();
        validUser.setEmailAddress("user@gmail");
        validUser.setFirstName("");
        validUser.setLastName("");
        validUser.setPassword1("");
        validUser.setPassword2("");

        Errors errors = new BeanPropertyBindingResult(validUser, "validUser");
        userInfoValidator.necessaryFields(validUser, errors);
        assertTrue(errors.hasErrors());
        assertNotNull( errors.getFieldError("emailAddress") );
        assertNotNull( errors.getFieldError("firstName") );
        assertNotNull( errors.getFieldError("lastName") );
        assertNotNull( errors.getFieldError("password1") );
        assertNotNull( errors.getFieldError("password2") );
    }*/

    /*@Test
    public void necessaryFieldsValidTest()
    {
        UserInfo validUser = new UserInfo();
        validUser.setEmailAddress("user@gmail.com");
        validUser.setFirstName("Vladislav");
        validUser.setLastName("Pikus");
        validUser.setPassword1("verysecredpassword");
        validUser.setPassword2("verysecredpassword");

        Errors errors = new BeanPropertyBindingResult(validUser, "validUser");
        userInfoValidator.necessaryFields(validUser, errors);
        assertFalse(errors.hasErrors());
    }*/
}
