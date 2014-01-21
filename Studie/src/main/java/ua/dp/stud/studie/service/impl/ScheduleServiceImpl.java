package ua.dp.stud.studie.service.impl;


import ua.dp.stud.studie.dao.ScheduleDao;
import ua.dp.stud.studie.model.Course;
import ua.dp.stud.studie.model.Faculties;
import ua.dp.stud.studie.service.ScheduleService;
import ua.dp.stud.studie.model.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("scheduleService")
@Transactional
public class ScheduleServiceImpl implements ScheduleService{

    @Autowired
    private ScheduleDao dao;

    public void setDao(ScheduleDao dao){
        this.dao=dao ;
    }
    @Override
    @Transactional(readOnly = true)
    public Schedule getScheduleById(Long id) {
        return dao.getScheduleById(id);
    }

    @Override
    @Transactional(readOnly = false)
    public /*void*/Schedule addSchedule(Schedule schedule) {
        return dao.addSchedule(schedule);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteSchedule(Long id) {
        dao.deleteSchedule(id);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateSchedule(Schedule schedule) {
        dao.updateSchedule(schedule);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Schedule> getAll() {
        return dao.getAll();
    }

    public Schedule getScheduleByFacultyAndYear(Faculties faculty, Course year)
    {
        return dao.getScheduleByFacultyAndYear(faculty,year);
    }


}
