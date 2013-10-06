package ua.dp.stud.studie.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ua.dp.stud.studie.dao.CourseDao;
import ua.dp.stud.studie.model.Course;

import java.util.Collection;

/**
 *           @author Nazarenko K.V.
 */

@Repository("courseDao")
public class CourseDaoImpl implements CourseDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

    @Override
    public Course getCourseById(Integer id) {
        return (Course) getSession().get(Course.class, id);
    }

    @Override
    public void addCourse(Course course) {
        getSession().save(course);
    }

    @Override
    public void deleteCourse(Integer id) {
        Course course = (Course) getSession().get(Course.class, id);
        getSession().delete(course);
    }

    @Override
    public void updateCourse(Course course) {
        getSession().update(course);
    }

    @Override

    public Collection<Course> getAll() {
        return getSession().createCriteria(Course.class).list();
    }


}
