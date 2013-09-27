package ua.dp.stud.studie.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Vladislav Pikus
 */
public class FacultiesTest {
    Faculties instance;

    @Before
    public void setUp() {
        instance = new Faculties();
    }

    @Test
    public void testGetId() {
        Integer expResult = null;
        Integer result = instance.getId();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetId() {
        instance.setId(100);
    }

    @Test
    public void testGetSpecialties() {
        List<Specialties> expResult = null;
        List<Specialties> result = instance.getSpecialties();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetSpecialties() {
        List<Specialties> specialtieses = new ArrayList();
        instance.setSpecialties(specialtieses);
    }

    @Test
    public void testSetNameOfFaculties() {
        String expResult = null;
        String result = instance.getNameOfFaculties();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetNameOfFaculties() {
        instance.setNameOfFaculties("");
    }

    @Test
    public void testSetBase() {
        Studie expResult = null;
        Studie result = instance.getBase();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetBase() {
        instance.setBase(new Studie());
    }
}
