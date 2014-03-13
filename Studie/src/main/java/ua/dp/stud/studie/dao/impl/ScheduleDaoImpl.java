package ua.dp.stud.studie.dao.impl;


import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.dp.stud.studie.dao.ScheduleDao;
import ua.dp.stud.studie.model.Faculties;
import ua.dp.stud.studie.model.Schedule;
import ua.dp.stud.studie.model.Course;

import java.util.List;

@Repository("scheduleDao")
public class ScheduleDaoImpl implements  ScheduleDao{
    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

    @Override
    public Schedule getScheduleById(Long id){
        Schedule schedule = (Schedule) getSession().get(Schedule.class, id);
        return schedule;
    }

    @Override
    public /*void*/Schedule addSchedule(Schedule schedule){
        getSession().save(schedule);

        return schedule;
    }

    @Override
    public void deleteSchedule(Long id){
        Schedule schedule = (Schedule) getSession().get(Schedule.class, id);
        getSession().delete(schedule);
    }

    @Override
    public void updateSchedule(Schedule schedule){
        getSession().update(schedule);
    }
    @Override
    public List<Schedule> getAll() {
        return getSession().createCriteria(Schedule.class).list();
    }

    @Override
    public Schedule getScheduleByFacultyAndYear(Faculties faculty, Course year){
        Query query = getSession().createQuery("from Schedule where faculty = :faculty1 and year = :year1 and " +
                "lastUpdateDate=(Select MAX(lastUpdateDate) from Schedule)");
        query.setParameter("faculty1", faculty);
        query.setParameter("year1",  year);
        Schedule schedule = (Schedule)query.uniqueResult();
        return schedule;

    }







}
