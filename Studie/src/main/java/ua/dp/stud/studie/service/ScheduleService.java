package ua.dp.stud.studie.service;

import ua.dp.stud.studie.model.Course;
import ua.dp.stud.studie.model.Faculties;
import ua.dp.stud.studie.model.Schedule;
import java.util.List;

public interface ScheduleService {
    Schedule getScheduleById(Long id);

    void addSchedule(Schedule schedule);

    void deleteSchedule(Long id);

    void updateSchedule(Schedule schedule);

    List<Schedule> getAll();

    Schedule getScheduleByFacultyAndYear(Faculties faculty, Course year);

}
