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
             /*
    @Test
    public void TestGet()
    {
        String expResult=null;
        String result=instance.getAuthorslogin();
        assertEquals(expResult,result);
    }

    @Test
    public void TestSet()
    {
        String authorsLogin="";
        instance.setAuthorslogin(authorsLogin);
    }      */

}
