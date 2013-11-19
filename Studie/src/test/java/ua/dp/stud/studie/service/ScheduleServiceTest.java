package ua.dp.stud.studie.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import ua.dp.stud.studie.dao.ScheduleDao;
import ua.dp.stud.studie.model.Schedule;
import ua.dp.stud.studie.service.impl.ScheduleServiceImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ScheduleServiceTest {
    private ScheduleService service;
    private ScheduleDao mockDao;
    private Schedule schedule1, schedule2;

    @Before
    public void init() {
        service = new ScheduleServiceImpl();
        mockDao = mock(ScheduleDao.class);
        ((ScheduleServiceImpl) service).setDao(mockDao);
        schedule1 = new Schedule();
        schedule1.setId(1L);

        schedule2 = new Schedule();
        schedule2.setId(2L);
    }
    @Test
    public void testGetScheduleById() {
        when(mockDao.getScheduleById(1L)).thenReturn(schedule1);
        assertEquals(schedule1, service.getScheduleById(1L));

        assertNull(service.getScheduleById(0L));
    }
}
