package ua.dp.stud.studie.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Vladislav Pikus
 */
public class StudieTest {
    private Studie instance;

    @Before
    public void setUp() {
        instance = new Studie();
    }

    @Test
    public void testGetTitle() {
        String expResult = null;
        String result = instance.getTitle();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetTitle() {
        String topic = "";
        instance.setTitle(topic);
    }

    @Test
    public void testGetText() {
        String expResult = null;
        String result = instance.getText();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetText() {
        String text = "";
        instance.setText(text);
    }

    @Test
    public void testGetCity() {
        String expResult = null;
        String result = instance.getCity();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetCity() {
        String str = "";
        instance.setCity(str);
    }

    @Test
    public void testGetYears() {
        Integer expResult = null;
        Integer result = instance.getYears();
        assertEquals(expResult, result);    }

    @Test
    public void testSetYears() {
        Integer year = 1900;
        instance.setYears(year);
    }

    @Test
    public void testGetStatus() {
        String expResult = null;
        String result = instance.getStatus();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetStatus() {
        String str = "";
        instance.setStatus(str);
    }

    @Test
    public void testGetAccreditacion() {
        String expResult = null;
        String result = instance.getAccreditacion();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetAccreditacion() {
        String str = "";
        instance.setAccreditacion(str);
    }

    @Test
    public void testGetFormOfTraining() {
        String expResult = null;
        String result = instance.getFormOfTraining();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetFormOfTraining() {
        String str = "";
        instance.setFormOfTraining(str);
    }

    @Test
    public void testGetQualificationLevel() {
        String expResult = null;
        String result = instance.getQualificationLevel();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetQualificationLevel() {
        String str = "";
        instance.setQualificationLevel(str);
    }

    @Test
    public void testGetAdress() {
        String expResult = null;
        String result = instance.getAdress();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetAdress() {
        String str = "";
        instance.setAdress(str);
    }

    @Test
    public void testGetPhone() {
        String expResult = null;
        String result = instance.getPhone();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetPhone() {
        String str = "";
        instance.setPhone(str);
    }

    @Test
    public void testGetPhoneAdmissions() {
        String expResult = null;
        String result = instance.getPhoneAdmissions();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetPhoneAdmissions() {
        String str = "";
        instance.setPhoneAdmissions(str);
    }

    @Test
    public void testGetWebsite() {
        String expResult = null;
        String result = instance.getWebsite();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetWebsite() {
        String str = "";
        instance.setWebsite(str);
    }

    @Test
    public void testGetFreeTrainig() {
        Boolean expResult = null;
        Boolean result = instance.getFreeTrainig();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetFreeTrainig() {
        instance.setFreeTrainig(true);
    }

    @Test
    public void testGetPaidTrainig() {
        Boolean expResult = null;
        Boolean result = instance.getPaidTrainig();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetPaidTrainig() {
        instance.setPaidTrainig(true);
    }

    @Test
    public void testGetMilitaryDepartment() {
        Boolean expResult = null;
        Boolean result = instance.getMilitaryDepartment();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetMilitaryDepartment() {
        instance.setMilitaryDepartment(true);
    }

    @Test
    public void testGetHostel() {
        Boolean expResult = null;
        Boolean result = instance.getHostel();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetHostel() {
        instance.setHostel(true);
    }

    @Test
    public void testGetPostgraduateEducation() {
        Boolean expResult = null;
        Boolean result = instance.getPostgraduateEducation();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetPostgraduateEducation() {
        instance.setPostgraduateEducation(true);
    }

    @Test
    public void testGetPostgraduateAndDoctoralStudies() {
        Boolean expResult = null;
        Boolean result = instance.getPostgraduateAndDoctoralStudies();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetPostgraduateAndDoctoralStudies() {
        instance.setPostgraduateAndDoctoralStudies(true);
    }

    @Test
    public void testGetPreparatoryDepartment() {
        Boolean expResult = null;
        Boolean result = instance.getPreparatoryDepartment();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetPreparatoryDepartment() {
        instance.setPreparatoryDepartment(true);
    }

    @Test
    public void testGetCountOfStudents() {
        Integer expResult = null;
        Integer result = instance.getCountOfStudents();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetCountOfStudents() {
        instance.setCountOfStudents(100);
    }

    @Test
    public void testGetCountOfTeachers() {
        Integer expResult = null;
        Integer result = instance.getCountOfTeachers();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetCountOfTeachers() {
        instance.setCountOfTeachers(100);
    }

    @Test
    public void testGetCountOfCandidates() {
        Integer expResult = null;
        Integer result = instance.getCountOfCandidates();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetCountOfCandidates() {
        instance.setCountOfCandidates(100);
    }

    @Test
    public void testGetCountOfProfessors() {
        Integer expResult = null;
        Integer result = instance.getCountOfProfessors();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetCountOfProfessors() {
        instance.setCountOfProfessors(100);
    }

    @Test
    public void testGetOnGraduation() {
        String expResult = null;
        String result = instance.getOnGraduation();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetOnGraduation() {
        instance.setOnGraduation("");
    }

    @Test
    public void testGetEnrollees() {
        String expResult = null;
        String result = instance.getEnrollees();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetEnrollees() {
        instance.setEnrollees("");
    }

    @Test
    public void testGetFaculties() {
        List<Faculties> expResult = null;
        List<Faculties> result = instance.getFaculties();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetFaculties() {
        List<Faculties> facultieses = new ArrayList();
        instance.setFaculties(facultieses);
    }

    @Test
    public void testHashCode() {
        Integer result = instance.hashCode();
        assertNotNull(result);
    }

    @Test
    public void testEquals() {
        assertFalse(instance.equals(null));
        assertTrue(instance.equals(instance));
        assertFalse(instance.equals(new Integer(5)));
        Studie newStadie = new Studie();
        assertTrue(instance.equals(newStadie));
        newStadie.setTitle("New Title");
        assertFalse(instance.equals(newStadie));
    }

    @Test
    public void testToString() {
        assertNotNull(instance.toString());
    }
}
