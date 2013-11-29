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
import ua.dp.stud.StudPortalLib.model.FileSaver;
import ua.dp.stud.studie.dao.ScheduleDao;
import ua.dp.stud.studie.model.Faculties;
import ua.dp.stud.studie.model.Schedule;
import ua.dp.stud.studie.model.Course;

import java.util.Date;

import static org.junit.Assert.assertEquals;

@ContextConfiguration(locations = {"classpath:/DaoTestContext.xml"})
@TransactionConfiguration(defaultRollback = true)
@RunWith(SpringJUnit4ClassRunner.class)
public class ScheduleDaoImplTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    @Qualifier(value = "scheduleDao")
    private ScheduleDao dao;
    private static  Schedule schedule1 ;
    private static Faculties faculties;
    public ScheduleDaoImplTest()
    {

    }
    public void setDao(ScheduleDao dao) {
        this.dao = dao;

    }
    @Before
    @Rollback(false)
    public void setUpClass() {
        FileSaver fs= new FileSaver();
        faculties = new Faculties();
        Date date = new Date();
        schedule1 = new Schedule(faculties, Course.FIRST,fs,date );
        dao.addSchedule(schedule1);

    }

    @Test
    public void testGetById() {
        Schedule schedule = dao.getScheduleById(schedule1.getId());
        assertEquals(schedule, schedule1);
    }

/*    @Test
    public void testGetByFacultyAndYear()
    {
        Schedule schedule = dao.getScheduleByFacultyAndYear(faculties, Course.FIRST)  ;
        assertEquals(schedule,schedule1);
    }*/
}
