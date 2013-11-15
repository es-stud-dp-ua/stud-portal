package ua.dp.stud.studie.service;

import ua.dp.stud.studie.model.Schedule;
import java.util.List;

public interface ScheduleService {
    Schedule getScheduleByID(Integer id);

    void addSchedule(Schedule schedule);

    void deleteSchedule(Integer id);

    void updateSchedule(Schedule schedule);

    List<Schedule> getAll();

}
