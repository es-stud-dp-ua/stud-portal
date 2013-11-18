package ua.dp.stud.studie.dao;
import ua.dp.stud.studie.model.Schedule;

import java.util.List;

public interface ScheduleDao {
    Schedule getScheduleById(Long id);

    void addSchedule(Schedule schedule);

    void deleteSchedule(Long id);

    void updateSchedule(Schedule schedule);

    List<Schedule> getAll();







}
