package ua.dp.stud.studie.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import ua.dp.stud.studie.dao.CouncilDao;
import ua.dp.stud.studie.model.Council;
import ua.dp.stud.studie.service.impl.CouncilServiceImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 */

    @RunWith(MockitoJUnitRunner.class)
    public class CouncilServiceTest {

        private CouncilService service;
        private CouncilDao mockDao;
        private Council council1, council2;


        @Before
        public void init() {
            service = new CouncilServiceImpl();
            mockDao = mock(CouncilDao.class);
            ((CouncilServiceImpl) service).setDao(mockDao);
            council1 = new Council();
            council1.setId(1);

            council2 = new Council();
            council2.setId(2);
        }


        @Test
        public void testGetCourseById() {
            when(mockDao.getCouncilById(1)).thenReturn(council1);
            assertEquals(council1, service.getCouncilById(1));

            assertNull(service.getCouncilById(0));
        }

    }
