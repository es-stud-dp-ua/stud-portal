/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.dp.stud.studie.dao.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import ua.dp.stud.studie.dao.StudieDao;
import ua.dp.stud.studie.model.Studie;

import static org.junit.Assert.assertEquals;

/**
 * @author Ольга
 */

@ContextConfiguration(locations = {"classpath:/DaoTestContext.xml"})
@TransactionConfiguration(defaultRollback = true)
@RunWith(SpringJUnit4ClassRunner.class)
public class StudieDaoImplTest extends AbstractTransactionalJUnit4SpringContextTests {
    private static final String STR = "New new new New new new New new new New new new New new new New new new New new new" +
            "New new new New new new New new new New new new New new new New new new New new new New new new New new new New new new" +
            "New new newNew new newNew new newNew new newNew new newNew new newNew new newNew new newNew new new";
    @Autowired
    @Qualifier(value = "studieDao")
    private StudieDao dao;
    private static Studie stud1;

    public StudieDaoImplTest() {
    }

    public void setDao(StudieDao dao) {
        this.dao = dao;
    }

    @Before
    @Rollback(false)
    public void setUpClass() {
        stud1 = new Studie("New new new",STR , "Dnipropetrovsk",
                "status", 1900, "IV", "new new new", "new new new", "new new new", "0562585632", "805662569856, 805662568856",
                "http://dnu.dp.ua", true, true, true, true, true, true, true, 100, 100, 100, 100, STR);
        stud1.setOnGraduation("11212 121 ");
        dao.save(stud1);
    }

    @Test
    public void testGetById() {
        Studie studie = dao.getById(stud1.getId());
        assertEquals(studie, stud1);
    }
}