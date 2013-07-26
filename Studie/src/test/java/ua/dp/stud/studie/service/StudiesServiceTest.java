/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.

package ua.dp.stud.studie.service;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import ua.dp.stud.studie.dao.StudieDao;
import ua.dp.stud.studie.model.Studie;
import ua.dp.stud.studie.service.impl.StudieServiceImpl;
import ua.dp.stud.StudPortalLib.model.ImageImpl;

import java.util.Arrays;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;
import ua.dp.stud.studie.service.StudieService;
import ua.dp.stud.studie.service.impl.StudieServiceImpl;


/**
 * @author Ольга

@RunWith(MockitoJUnitRunner.class)
public class StudiesServiceTest {

    private StudieService service;
    private StudieDao mockDao;
    private Studie stud1, stud2;


    @Before
    public void init() {
        service = new StudieServiceImpl();
        mockDao = mock(StudieDao.class);
        ((StudieServiceImpl) service).setDao(mockDao);
        stud1 = new Studie();
        stud1.setId(1);

        stud2 = new Studie();
        stud2.setId(2);
    }


    @Test
    public void testGetStudieById() {
        when(mockDao.getStudieById(1)).thenReturn(stud1);
        assertEquals(stud1, service.getStudieById(1));

        assertNull(service.getStudieById(0));
    }


    @Test
    public void testGetAllStudies() {
        ArrayList<Studie> allStudies = new ArrayList<Studie>(Arrays.asList(stud1, stud2));
        when(mockDao.getAllStudies()).thenReturn(allStudies);
        assertEquals(allStudies, service.getAllStudies());

        when(mockDao.getAllStudies()).thenReturn(null);
        assertNull(service.getAllStudies());
    }

    @Test
    public void testAddStudie() {
        service.addStudie(stud1);
        verify(mockDao, times(1)).addStudie(stud1);
    }


    @Test
    public void testUpdateStudie() {
        service.updateStudie(stud1);
        verify(mockDao, times(1)).updateStudie(stud1);
    }

    @Test
    public void testAddImage() {
        ImageImpl image = new ImageImpl();
        service.addImage(image);
        verify(mockDao, times(1)).addImage(image);
    }


    @Test
    public void testDeleteStudie() {
        service.addStudie(stud1);
        Integer id = stud1.getId();
        service.deleteStudie(stud1);
        verify(mockDao, times(1)).deleteStudie(id);
    }

}
 */