/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.

package ua.dp.stud.studie.model;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Ольга

public class StudieTest {
    public StudieTest() {
    }

    private Studie instance;

    @Before
    public void setUp() {
        instance = new Studie();
    }

    @Test
    public void testGetId() {
        Integer expResult = null;
        Integer result = instance.getId();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetId() {
        Integer id = null;
        instance.setId(id);
    }

    @Test
    public void testGetTopic() {
        String expResult = null;
        String result = instance.getTitle();
        assertEquals(expResult, result);
    }


    @Test
    public void testSetTopic() {
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
    public void testHashCode() {
        Integer result = instance.hashCode();
        assertNotNull(result);
    }
 @Test
    public void getFacultiesOfStudie() {
        Studie stud3 = new Studie();
        List<Faculties> f=new ArrayList<Faculties>();
        Faculties ff=new Faculties();
        ff.setNameOfFaculties("ololo"); 
        f.add(ff); 
        stud3.setFaculties(f);
        List<Faculties> f2=stud3.getFaculties(); 
        assertEquals(f, f2);
    }

} */
