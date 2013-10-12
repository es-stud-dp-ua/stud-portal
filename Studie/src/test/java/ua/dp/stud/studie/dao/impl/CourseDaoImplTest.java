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
/*import ua.dp.stud.studie.dao.CourseDao;
import ua.dp.stud.studie.model.Course;
import ua.dp.stud.studie.model.CoursesType;
import ua.dp.stud.studie.model.KindOfCourse;

import static org.junit.Assert.assertEquals;
*/
/**
 *
 */
@ContextConfiguration(locations = {"classpath:/DaoTestContext.xml"})
@TransactionConfiguration(defaultRollback = true)
@RunWith(SpringJUnit4ClassRunner.class)
public class CourseDaoImplTest   extends AbstractTransactionalJUnit4SpringContextTests {
         /*
        private static final String STR = "New new new New new new New new new New new new New new new New new new New new new" +
                "New new new New new new New new new New new new New new new New new new New new new New new new New new new New new new" +
                "New new newNew new newNew new newNew new newNew new newNew new newNew new newNew new newNew new new";
        @Autowired
        @Qualifier(value = "courseDao")
        private CourseDao dao;
        private static Course course1;

        public CourseDaoImplTest() {
        }

        public void setDao(CourseDao dao) {
            this.dao = dao;
        }

        @Before
        @Rollback(false)
        public void setUpClass() {

            //course1 = new Course("","","",new KindOfCourse(""),false, new CoursesType.Tutor, )
            //course1.setOnGraduation("11212 121 ");
            //dao.save(course1);
        }

        @Test
        public void testGetById() {
            Course course = dao.getById(course1.getId());
            assertEquals(course, course1);
        }  */
}
