package ua.dp.stud.studie.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dp.stud.studie.dao.CourseDao;
import ua.dp.stud.studie.model.Course;
import ua.dp.stud.studie.service.CourseService;

import java.util.Collection;

/**
 * @author Nazarenko Alexandra
 */

@Service("courceService")
@Transactional
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseDao dao;

    public void setDao(CourseDao dao){
        this.dao=dao ;
    }



    @Override
    @Transactional(readOnly = true)
    public Course getCourseByID(Integer id) {
     return dao.getCourseById(id);
    }

    @Override
    @Transactional(readOnly = false)
    public void addCourse(Course course) {
         dao.addCourse(course);
    }

    @Override
    @Transactional(readOnly = false)
     public void deleteCourse(Integer id) {
        dao.deleteCourse(id);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateCourse(Course course) {
        dao.updateCourse(course);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Course> getAll() {
        return dao.getAll();
    }
}
