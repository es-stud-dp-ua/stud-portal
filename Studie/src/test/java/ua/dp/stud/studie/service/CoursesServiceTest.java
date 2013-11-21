package ua.dp.stud.studie.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import ua.dp.stud.StudPortalLib.service.CourseService;
import ua.dp.stud.StudPortalLib.dao.CourseDao;
import ua.dp.stud.StudPortalLib.model.Course;
import ua.dp.stud.StudPortalLib.service.impl.CourseServiceImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 */

    @RunWith(MockitoJUnitRunner.class)
    public class CoursesServiceTest {

        private CourseService service;
        private CourseDao mockDao;
        private Course course1, course2;


        @Before
        public void init() {
            service = new CourseServiceImpl();
            mockDao = mock(CourseDao.class);
            ((CourseServiceImpl) service).setDao(mockDao);
            course1 = new Course();
            course1.setId(1);

            course2 = new Course();
            course2.setId(2);
        }


        @Test
        public void testGetCourseById() {
            when(mockDao.getCourseById(1)).thenReturn(course1);
            assertEquals(course1, service.getCourseByID(1));

            assertNull(service.getCourseByID(0));
        }

    }
