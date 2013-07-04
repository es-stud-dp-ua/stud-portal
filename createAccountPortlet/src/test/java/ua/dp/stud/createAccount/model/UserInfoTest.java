package ua.dp.stud.createAccount.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Author: Tradunsky V.V.
 * Date: 19.03.13
 */
public class UserInfoTest
{
    private UserInfo user;

    @Before
    public void setUp()
    {
        this.user = new UserInfo();
    }

    @Test
    public void getLastNameTest()
    {
        String result = null;
        String expResult = this.user.getLastName();
        assertEquals(result, expResult);
    }

    @Test
    public void setLastNameTest()
    {
        String lastName = null;
        this.user.setLastName(lastName);
    }

    @Test
    public void getFirstNameTest()
    {
        String result = null;
        String expResult = this.user.getFirstName();
        assertEquals(result, expResult);
    }

    @Test
    public void setFirstNameTest()
    {
        String firstName = null;
        this.user.setFirstName(firstName);
    }

    @Test
    public void getEmailAddressTest()
    {
        String result = null;
        String expResult = this.user.getEmailAddress();
        assertEquals(result, expResult);
    }

    @Test
    public void setEmailAddressTest()
    {
        String emailAddress = null;
        this.user.setEmailAddress(emailAddress);
    }

    @Test
    public void getPassword1Test()
    {
        String result = null;
        String expResult = this.user.getPassword1();
        assertEquals(result, expResult);
    }

    @Test
    public void  setPassword1Test()
    {
        String password1 = null;
        this.user.setPassword1(password1);
    }

    @Test
    public void getPassword2Test()
    {
        String result = null;
        String expResult = this.user.getPassword2();
        assertEquals(result, expResult);
    }

    @Test
    public void  setPassword2Test()
    {
        String password2 = null;
        this.user.setPassword2(password2);
    }

    @Test
    public void getPlaceOfStudyTest()
    {
        String result = null;
        String expResult = this.user.getPlaceOfStudy();
        assertEquals(result, expResult);
    }

    @Test
    public void  setPlaceOfStudyTest()
    {
        String placeOfStudy = null;
        this.user.setPlaceOfStudy(placeOfStudy);
    }

    @Test
    public void getGroupTest()
    {
        String result = null;
        String expResult = this.user.getGroup();
        assertEquals(result, expResult);
    }

    @Test
    public void setGroupTest()
    {
        String group = null;
        this.user.setGroup(group);
    }

    @Test
    public void getVkontakteIdTest()
    {
        String result = null;
        String expResult = this.user.getVkontakteId();
        assertEquals(result, expResult);
    }

    @Test
    public void setVkontakteIdTest()
    {
        String vkontakteId = null;
        this.user.setVkontakteId(vkontakteId);
    }

    @Test
    public void getFacebookIdTest()
    {
        String result = null;
        String expResult = this.user.getFacebookId();
        assertEquals(result, expResult);
    }

    @Test
    public void setFacebookIdTest()
    {
        String facebookId = null;
        this.user.setFacebookId(facebookId);
    }

    @Test
    public void isRules()
    {
        Boolean result = false;
        Boolean expResult = this.user.isRules();
        assertEquals(result, expResult);
    }

    @Test
    public void setRulesTest()
    {
        Boolean rules = false;
        this.user.setRules(rules);
    }

    @Test
    public void getAboutMeTest()
    {
        String result = null;
        String expResult = this.user.getAboutMe();
        assertEquals(result, expResult);
    }

    @Test
    public void setAboutMeTest()
    {
        String aboutMe = null;
        this.user.setAboutMe(aboutMe);
    }

    @Test
    public void getFacultyTest()
    {
        String result = null;
        String expResult = this.user.getFaculty();
        assertEquals(result, expResult);
    }

    @Test
    public void  setFacultyTest()
    {
        String faculty = null;
        this.user.setFaculty(faculty);
    }

}
