package ua.dp.stud.studie.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Vladislav Pikus
 */
public class SpecialtiesTest {
    Specialties instance;

    @Before
    public void setUp() {
        instance = new Specialties();
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
    public void testSetNameOfFaculties() {
        instance.setNameOfFaculties(new Faculties());
    }

    @Test
    public void testGetNameOfFaculties() {
        Faculties expResult = null;
        Faculties result = instance.getNameOfFaculties();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetNameOfSpecialties() {
        instance.setNameOfSpecialties("");
    }

    @Test
    public void testGetNameOfSpecialties() {
        String expResult = null;
        String result = instance.getNameOfSpecialties();
        assertEquals(expResult, result);
    }
}
