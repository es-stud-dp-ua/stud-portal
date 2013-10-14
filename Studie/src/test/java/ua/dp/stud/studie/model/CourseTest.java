package ua.dp.stud.studie.model;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Nazarenko Alexandra
 */
public class CourseTest {

    private Course instance;

    @Before
    public void SetUp()
    {
        instance=new Course();
    }

    @Test
    public void TestGetCourseName()
    {
        String expResult=null;
        String result=instance.getCourseName();
        assertEquals(expResult,result);
    }

    @Test
    public void TestSetTitle()
    {
        String courseName="";
        instance.setCourseName(courseName);
    }

    @Test
    public void TestGetAuthorsLogin()
    {
        String expResult=null;
        String result=instance.getAuthorslogin();
        assertEquals(expResult,result);
    }

    @Test
    public void TestSetAuthorsLogin()
    {
        String authorsLogin="";
        instance.setAuthorslogin(authorsLogin);
    }

    @Test
    public void TestGetCoursesContact()
    {
        String expResult=null;
        String result=instance.getCoursesContact();
        assertEquals(expResult,result);
    }

    @Test
    public void TestSetCoursesContact()
    {
        String coursesContact="";
        instance.setCoursesContact(coursesContact);
    }

    @Test
    public void TestGetCoursesDescription()
    {
        String expResult=null;
        String result=instance.getCoursesDescription();
        assertEquals(expResult,result);
    }

    @Test
    public void TestSetCoursesDescription()
    {
        String coursesDescription="";
        instance.setCoursesContact(coursesDescription);
    }

    @Test
    public void TestGetKindOfCourse()
    {
        KindOfCourse expResult=null;
        KindOfCourse result=instance.getKindOfCourse();
        assertEquals(expResult,result);
    }

    @Test
    public void TestSetKindOfCourse()
    {
        KindOfCourse kindOfCourse = new KindOfCourse("");
        instance.setKindOfCourse(kindOfCourse);
    }

    @Test
    public void TestGetAddState()
    {
        Boolean expResult=false;
        Boolean result=instance.getAddState();
        assertEquals(expResult,result);
    }

    @Test
    public void TestSetAddState()
    {
        Boolean state = false;
        instance.setAddState(state);
    }

}
