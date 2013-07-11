/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.dp.stud.StudPortalLib.dao.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import ua.dp.stud.StudPortalLib.dao.StudieDao;
import ua.dp.stud.StudPortalLib.model.Studie;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * @author Ольга
 */
@ContextConfiguration(locations = {"classpath:/DaoTestContext.xml"})
@TransactionConfiguration(defaultRollback = true)
@RunWith(SpringJUnit4ClassRunner.class)
public class StudieDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private StudieDao dao;
    private static Studie stud1;
    private static Studie stud2;

    public StudieDaoTest() {
    }

    public void setDao(StudieDao dao) {
        this.dao = dao;
    }

    @Before
    @Rollback(false)
    public void setUpClass() {
        stud1 = new Studie();
        stud2 = new Studie();
        stud1.setTitle("DNU univer");
        stud1.setText("We are DNU!");
        stud2.setTitle("Other univer");
        stud2.setText("We are other!");

        dao.addStudie(stud1);
        dao.addStudie(stud2);
    }

    @Test
    public void AddStud() {
        Studie stud3 = new Studie();

        assertNull(stud3.getId());
        dao.addStudie(stud3);
        assertNotNull(stud3.getId());
    }

    @Test
    public void getById() {
        Integer id = stud1.getId();

        assertEquals(stud1, dao.getStudieById(id));
    }

    @Test
    public void getAll() {
        Set<Studie> studsList = new HashSet<Studie>(Arrays.asList(stud1, stud2));
        Set<Studie> fromDao = new HashSet<Studie>(dao.getAllStudies());

        assertEquals(studsList, fromDao);
        assertEquals(2, fromDao.size());
    }

}
