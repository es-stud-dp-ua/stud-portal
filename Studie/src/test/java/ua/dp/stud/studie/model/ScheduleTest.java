package ua.dp.stud.studie.model;


import org.junit.Before;
import org.junit.Test;
import ua.dp.stud.StudPortalLib.model.FileSaver;

import static org.junit.Assert.assertEquals;

public class ScheduleTest {
    private Schedule instance;
    @Before
    public void SetUp()
    {
        instance=new Schedule();
    }

    @Test
    public void TestGetUniversity()
    {
        Studie expResult=null;
        Studie result=instance.getUniversity();
        assertEquals(expResult,result);
    }

    @Test
    public void TestSetUniversity()
    {
        Studie university = new Studie();
        instance.setUniversity(university);
    }

    @Test
    public void TestGetFaculty()
    {
        Faculties expResult=null;
        Faculties result=instance.getFaculty();
        assertEquals(expResult,result);
    }

    @Test
    public void TestSetFaculty()
    {
        Faculties faculty = new Faculties();
        instance.setFaculty(faculty);
    }

    @Test
    public void TestGetYear()
    {
        Years expResult=Years.FIRST;
        Years result=instance.getYear();
        assertEquals(expResult,result);
    }

    @Test
    public void TestSetYear()
    {
        Years year = Years.FIRST;
        instance.setYear(year);
    }

    @Test
    public void TestGetFile()
    {

        FileSaver expResult = null;
        FileSaver result = instance.getScheduleFile();
        assertEquals(expResult,result);
    }

    @Test
    public void TestSetFile()
    {
        FileSaver fileSaver = new FileSaver();
        instance.setScheduleFile(fileSaver);
    }






}
