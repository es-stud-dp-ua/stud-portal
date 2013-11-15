package ua.dp.stud.studie.dao.impl;


import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.dp.stud.studie.dao.ScheduleDao;
import ua.dp.stud.studie.model.Schedule;

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
    public Schedule getScheduleById(Integer id)
    {
        Schedule schedule = (Schedule) getSession().get(Schedule.class, id);
        return schedule;
    }

    @Override
    public void addSchedule(Schedule schedule)
    {
        getSession().save(schedule);
    }

    @Override
    public void deleteSchedule(Integer id)
    {
        Schedule schedule = (Schedule) getSession().get(Schedule.class, id);
        getSession().delete(schedule);
    }

    @Override
    public void updateSchedule(Schedule schedule)
    {
        getSession().update(schedule);
    }
    @Override
    public List<Schedule> getAll() {
        return getSession().createCriteria(Schedule.class).list();
    }







}
