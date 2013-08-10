/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
 */
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
        when(mockDao.getById(1)).thenReturn(stud1);
        assertEquals(stud1, service.getStudieById(1));

        assertNull(service.getStudieById(0));
    }

}